package com.example.beluniapp.navigation


sealed class Screens(val route: String){
    object Home: Screens("home_screen")
    object WishList: Screens("wishlist_screen")
    object Account: Screens("account_screen")
    object OnBoarding: Screens("onboarding_screen")
    object SignIn: Screens("signin_screen")
    object SignUp: Screens("signup_screen")
    object UniDetails: Screens("unidetails_screen")
}