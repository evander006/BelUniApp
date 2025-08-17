package com.example.beluniapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.beluniapp.mvi.viewmodel.MainScreenViewModel
import com.example.beluniapp.navigation.BottomNavBar
import com.example.beluniapp.navigation.BottomNavBarWithCutOut
import com.example.beluniapp.navigation.Screens
import com.example.beluniapp.onBoarding.OnBoardingScreen
import com.example.beluniapp.onBoarding.OnBoardingUtils
import com.example.beluniapp.pages.AccountScreen
import com.example.beluniapp.pages.MainScreen
import com.example.beluniapp.pages.UniversityDetailScreen
import com.example.beluniapp.pages.WishlistScreen
import com.example.beluniapp.pages.signupin.SignIn
import com.example.beluniapp.pages.signupin.SignUp
import com.example.beluniapp.ui.theme.BelUniAppTheme
import com.example.data.UniItemCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme

class MainActivity : ComponentActivity() {
    private val onboardingUtils by lazy { OnBoardingUtils(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            BelUniAppTheme {
                Surface {
                    AppNavigation()
                }
            }
        }
    }

    @Composable
    private fun AppNavigation() {
        val navController = rememberNavController()
        val startDestination = if (onboardingUtils.isOnBoardingComplete()) {
            Screens.Home.route
        } else {
            Screens.OnBoarding.route
        }

        var navItemSelected by rememberSaveable { mutableStateOf(0) }
        Scaffold(
            bottomBar = {
                if (startDestination != Screens.OnBoarding.route &&
                    startDestination != Screens.SignIn.route &&
                    startDestination != Screens.SignUp.route
                ) {
                    Box(
                        Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.BottomCenter
                    ) {
                        BottomNavBarWithCutOut(
                            navController = navController,
                            navItemSelected = navItemSelected,
                            onItemSelected = { navItemSelected = it }
                        )

                        Box(
                            modifier = Modifier
                                .align(Alignment.TopCenter)
                                .offset(y = (-24).dp)
                                .size(58.dp)
                                .clip(CircleShape)
                                .background(
                                    if (navItemSelected == 1) MaterialTheme.colorScheme.onBackground else Color.Gray // активный цвет если выбрано
                                )
                                .clickable {
                                    navItemSelected = 1
                                    navController.navigate(Screens.WishList.route){
                                        launchSingleTop = true
                                        restoreState = true                                    }
                                },
                            contentAlignment = Alignment.Center
                        ){
                            Icon(
                                imageVector = Icons.Filled.Favorite,
                                contentDescription = stringResource(id = R.string.wishlist),
                                tint = Color.White,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                    }
                }
            }
        ) { paddingValues ->
            NavHost(
                navController = navController,
                startDestination = startDestination,
                modifier = Modifier.padding(paddingValues)
            ) {
                composable(Screens.OnBoarding.route) {
                    OnBoardingScreen {
                        onboardingUtils.setOnBoardingComplete()
                        navController.navigate(Screens.SignUp.route)
                    }
                }
                composable(Screens.SignUp.route) {
                    SignUp(
                        onSignUpSuccess = {
                            navController.navigate(Screens.Home.route) {
                                popUpTo(Screens.SignUp.route) { inclusive = true }
                            }
                        },
                        navigateToSignIn = {
                            navController.navigate(Screens.SignIn.route)
                        }
                    )
                }
                composable(Screens.SignIn.route) {
                    SignIn {
                        onboardingUtils.setOnBoardingComplete()
                        navController.navigate(Screens.Home.route) {
                            popUpTo(Screens.SignIn.route) { inclusive = true }
                        }
                    }
                }
                composable(Screens.Home.route) {
                    val viewmodel : MainScreenViewModel = viewModel()
                    MainScreen(
                        viewModel = viewmodel,
                        navigateToDetails = { uni ->
                        navController.currentBackStackEntry?.savedStateHandle?.set("uni", uni)

                        navController.navigate(Screens.UniDetails.route)
                    })

                }
                composable(Screens.WishList.route) {
                    WishlistScreen()
                }
                composable(Screens.Account.route) {
                    AccountScreen(navBack = {
                        navItemSelected=0
                        navController.navigate(Screens.Home.route){ popUpTo(Screens.Account.route) { inclusive = true }}
                    })
                }
                composable(
                    route = Screens.UniDetails.route
                ) {
                    val parentEntry= remember(navController) {
                        navController.getBackStackEntry(Screens.Home.route)
                    }
                    val viewmodel : MainScreenViewModel = viewModel(parentEntry)
                    val uni = navController.previousBackStackEntry?.savedStateHandle?.get<UniItemCard>("uni") ?: return@composable
                    UniversityDetailScreen(
                        uni = uni,
                        navigateBack = {
                            navController.navigate(Screens.Home.route) {
                                popUpTo(
                                    Screens.UniDetails.route
                                ) { inclusive = true }
                            }
                        },
                        viewmodel = viewmodel
                    )
                }
            }
        }
    }

}
