package com.example.beluniapp.mvi.viewmodel

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.beluniapp.utils.loadBirthdate
import com.example.beluniapp.utils.loadEmail
import com.example.beluniapp.utils.loadGender
import com.example.beluniapp.utils.loadImgPath
import com.example.beluniapp.utils.loadLastname
import com.example.beluniapp.utils.loadName
import com.example.beluniapp.utils.loadPhone
import com.example.beluniapp.utils.saveBirthdate
import com.example.beluniapp.utils.saveEmail
import com.example.beluniapp.utils.saveGender
import com.example.beluniapp.utils.saveImageToInternalStorage
import com.example.beluniapp.utils.saveImgPath
import com.example.beluniapp.utils.saveLastname
import com.example.beluniapp.utils.saveName
import com.example.beluniapp.utils.savePhone
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


class AccountScreenViewModel(private val appContext: Application): AndroidViewModel(appContext) {
    private val _imagePath= MutableLiveData<String?>()
    val imagePath: LiveData<String?> =_imagePath

    private val _firstname= MutableLiveData<String>()
    private val _lastname= MutableLiveData<String>()
    private val _phone= MutableLiveData<String>()
    private val _email= MutableLiveData<String>()
    private val _birthdate= MutableLiveData<Long>()
    private val _gender= MutableLiveData<String>()
    val fisrtname=_firstname
    val lastname=_lastname
    val email=_email
    val gender=_gender
    val birthdate=_birthdate
    val phone=_phone

    init {
        _imagePath.value= loadImgPath(appContext)
        viewModelScope.launch {
            _firstname.value = loadName(appContext)
            _lastname.value =loadLastname(appContext)
            _phone.value =loadPhone(appContext)
            _email.value =loadEmail(appContext)
            _birthdate.value = loadBirthdate(appContext)
            _gender.value = loadGender(appContext)
        }
    }
    fun onImageLoad(uri: Uri){
        viewModelScope.launch(Dispatchers.IO) {
            val savedpath= saveImageToInternalStorage(appContext,uri)
            saveImgPath(appContext, savedpath)
            launch(Dispatchers.Main) {
                _imagePath.value=savedpath
            }
        }
    }
    fun editName(name: String){
        viewModelScope.launch {
            saveName(appContext,name)
            _firstname.value= loadName(appContext)
        }
    }
    fun editLastname(lastname: String){
        viewModelScope.launch {
            saveLastname(appContext,lastname)
            _lastname.value= loadLastname(appContext)
        }
    }
    fun editEmail(email: String){
        viewModelScope.launch {
            saveEmail(appContext,email)
            _email.value=loadEmail(appContext)
        }
    }
    fun editBirthDate(birthdate: Long?){
        viewModelScope.launch {
            saveBirthdate(appContext,birthdate)
            _birthdate.value= loadBirthdate(appContext)
        }
    }
    fun editPhone(phone: String){
        viewModelScope.launch {
            savePhone(appContext,phone)
            _phone.value= loadPhone(appContext)
        }
    }
    fun editGender(gender: String){
        viewModelScope.launch {
            saveGender(appContext,gender)
            _gender.value= loadGender(appContext)
        }
    }

}