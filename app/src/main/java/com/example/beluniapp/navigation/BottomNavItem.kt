package com.example.beluniapp.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.example.beluniapp.R

data class BottomNavItem(
    val label: String="",
    val icon: ImageVector= Icons.Filled.Home,
    val route: String=""
){
    @Composable
    fun bottomNavigationItems():List<BottomNavItem>{
        return listOf(
            BottomNavItem(
                label = stringResource(id=R.string.home),
                icon = Icons.Filled.Home,
                route = Screens.Home.route),
            BottomNavItem(
                label = stringResource(id=R.string.wishlist),
                icon = Icons.Filled.Favorite,
                route = Screens.WishList.route),
            BottomNavItem(
                label = stringResource(id=R.string.account),
                icon = Icons.Filled.AccountCircle,
                route = Screens.Account.route),
        )
    }
}