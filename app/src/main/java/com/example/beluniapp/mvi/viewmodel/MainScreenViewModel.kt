package com.example.beluniapp.mvi.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beluniapp.mvi.intent.MainScreenIntent
import com.example.beluniapp.mvi.state.MainScreenState
import com.example.data.*
import com.example.data.roomdb.DatabaseProvider
import com.example.data.roomdb.WishlistUniversity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.Locale
import kotlin.coroutines.resumeWithException

class MainScreenViewModel(application: Application) : AndroidViewModel(application) {
    private val _state = MutableStateFlow(MainScreenState())
    private val db= DatabaseProvider.getDatabase(application)
    private val wishlistDao=db.wishlistDao()
    val state = _state.asStateFlow()
    val supportedLangs = listOf("en", "ru")
    val currentLang = Locale.getDefault().language
    val lang = if (currentLang in supportedLangs) currentLang else "en"

    private val _isSearching= MutableStateFlow(false)
    val isSearching=_isSearching.asStateFlow()
    private val _searchText= MutableStateFlow("")
    val searchText=_searchText.asStateFlow()
    val filteredunis= combine(_state, _searchText) {state, query ->
        if (query.isBlank()){
            state.universities
        }else{
           state.universities.filter {
               it.name.contains(query, ignoreCase = true)
           }
        }

   }.stateIn(
       scope = viewModelScope,
       started = SharingStarted.WhileSubscribed(5000),
       initialValue = emptyList()
   )

    fun onSearchTextChange(text: String){
        _searchText.value=text
    }

    fun onToggleSearch(){
        _isSearching.value=!_isSearching.value
        if (!_isSearching.value){
            onSearchTextChange("")
        }
    }

    fun handleIntent(intent: MainScreenIntent){
        when(intent){
            is MainScreenIntent.LoadUniversities -> fetchUniversities()
            is MainScreenIntent.AddFavourites -> toggleFavourite(intent.id)
        }
    }

    private fun fetchUniversities() {
        _state.update { it.copy(isLoading = true, error = null) }

        viewModelScope.launch {
            try {
                val wishlistIds = wishlistDao.getAllWishlistIds().first().toSet()
                val database = FirebaseDatabase.getInstance().getReference("universities/$lang")
                val result = suspendCancellableCoroutine<MutableList<UniItemCard>> { cont ->
                    database.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val tempList = mutableListOf<UniItemCard>()
                            for (child in snapshot.children) {
                                val uni = child.getValue(UniItemCard::class.java)
                                if (uni != null) {
                                    if (uni.id in wishlistIds) {
                                        uni.isFavourite = true
                                    }
                                    tempList.add(uni)
                                }
                            }
                            cont.resume(tempList) {}
                        }
                        override fun onCancelled(error: DatabaseError) {
                            cont.resumeWithException(Exception(error.message))
                        }
                    })
                }
                _state.update { it.copy(isLoading = false, universities = result, error = null) }

            } catch (e: CancellationException) {
                _state.update { it.copy(isLoading = false, error = e.message) }
            }
        }

    }

    fun toggleFavourite(id: Int) {
        viewModelScope.launch {
            val currUni=_state.value.universities.find { it.id==id } ?: return@launch
            val isAlreadyInWishlist =wishlistDao.isInWishlist(id)
            if (isAlreadyInWishlist){
                wishlistDao.removeFromWishlist(uni = WishlistUniversity(currUni.id, currUni.name, currUni.city, currUni.imageUrl,
                //    currUni.isFavourite
                ))
                //Log.d("RoomDB", "Removed to Room DB: ${currUni.name}")
            }else{
                wishlistDao.addToWishlist(uni = WishlistUniversity(currUni.id, currUni.name, currUni.city, currUni.imageUrl,
                //    currUni.isFavourite
                ))
                //Log.d("RoomDB", "Added to Room DB: ${currUni.name}")
            }
            val updatedList=_state.value.universities.map {
                if (it.id==id) it.copy(isFavourite = !isAlreadyInWishlist) else it
            }
            _state.update { it.copy(universities = updatedList) }
           // Log.i("item", "Toggled favourite for id=$id, new state: ${!isAlreadyInWishlist}")
        }
    }

    fun loadWishlist(){
        viewModelScope.launch {
            wishlistDao.getAll().collect { wishlisted->
                _state.update { it.copy(wishlist = wishlisted) }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        db.close()
    }
}
