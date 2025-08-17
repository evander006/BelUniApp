package com.example.beluniapp.onBoarding

import android.content.Context

class OnBoardingUtils(private val context: Context) {
    fun isOnBoardingComplete(): Boolean{
        return context.getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
            .getBoolean("completed",false)
    }
    fun setOnBoardingComplete(){
        context.getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
            .edit().putBoolean("completed",true).apply()
    }
}