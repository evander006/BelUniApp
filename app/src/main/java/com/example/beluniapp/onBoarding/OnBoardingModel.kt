package com.example.beluniapp.onBoarding

import androidx.annotation.DrawableRes
import com.example.beluniapp.R

sealed class OnBoardingModel(
    val image: Int,
    val title: String,
    val description: String,
) {
    data object FirstPage: OnBoardingModel(
        image = R.drawable.page1,
        title = "Find your dream university",
        description = "Explore a wide range of universities and programs tailored for Belarussian students."
    )
    data object SecondPage: OnBoardingModel(
        image = R.drawable.page2,
        title = "Find your future university",
        description = "Explore a comprehensive list of universities in Belarus, complete with detailed information about programs, admission requirements, and campus life."
    )
    data object ThirdPage: OnBoardingModel(
        image = R.drawable.page3,
        title = "Find your perfect university",
        description = "Explore a comprehensive list of universities in Belarus, complete with detailed information and student reviews."
    )
}