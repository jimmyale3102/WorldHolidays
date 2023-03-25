package dev.alejo.world_holidays.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.alejo.world_holidays.core.navigation.Navigation
import dev.alejo.world_holidays.core.navigation.Screen
import dev.alejo.world_holidays.ui.theme.*

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalAnimationApi::class)
    @SuppressLint("UnrememberedMutableState")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WorldHolidaysTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = BlueLight
                ) {
                    Navigation(
                        navHostController = rememberAnimatedNavController(),
                        startDestination = Screen.Home.route
                    )
                }
            }
        }
    }
}