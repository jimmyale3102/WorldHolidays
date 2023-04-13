package dev.alejo.world_holidays.ui.presentation.splash.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.alejo.world_holidays.core.navigation.Navigation
import dev.alejo.world_holidays.core.navigation.Screen
import dev.alejo.world_holidays.ui.presentation.splash.viewmodel.SplashViewModel
import dev.alejo.world_holidays.ui.theme.*

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: SplashViewModel by viewModels()

    @OptIn(ExperimentalAnimationApi::class)
    @SuppressLint("UnrememberedMutableState")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().setKeepOnScreenCondition {
            viewModel.isLoading.value
        }
        setContent {
            WorldHolidaysTheme {
                Navigation(
                    navHostController = rememberAnimatedNavController(),
                    startDestination = Screen.Home.route
                )
            }
        }
    }
}