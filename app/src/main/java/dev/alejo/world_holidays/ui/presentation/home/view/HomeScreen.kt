package dev.alejo.world_holidays.ui.presentation.home.view

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dev.alejo.world_holidays.data.model.Country
import dev.alejo.world_holidays.ui.composables.AboutIconButton
import dev.alejo.world_holidays.ui.presentation.home.view.components.HomeBackground
import dev.alejo.world_holidays.ui.composables.VerticalSpacer
import dev.alejo.world_holidays.ui.presentation.home.view.components.AutoCompleteSearchBar
import dev.alejo.world_holidays.ui.presentation.home.viewmodel.HomeViewModel
import dev.alejo.world_holidays.ui.theme.*

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun HomeScreen(navHostController: NavHostController, viewModel: HomeViewModel = hiltViewModel()) {
    viewModel.onCreate(navHostController)
    val holidayTitle by viewModel.holidayTitle.collectAsState()
    val holidayDescription by viewModel.holidayDescription.collectAsState()
    val searchValue by viewModel.searchValue.collectAsState()
    val dropdownExpanded by viewModel.dropdownExpanded.collectAsState()
    val dropdownOptions by viewModel.dropdownOptions.collectAsState()

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    HomeScreenContent(
        holidayTitle = holidayTitle,
        holidayDescription = holidayDescription,
        navHostController = navHostController,
        searchValue = searchValue,
        dropDownExpanded = dropdownExpanded,
        dropDownOptions = dropdownOptions,
        onDropdownDismissRequest = { viewModel.onDropdownDismissRequest() },
        onItemSelected = {
            viewModel.onItemSelected()
            keyboardController?.hide()
            focusManager.clearFocus()
        }
    ) { inputTyped ->
        viewModel.onSearchChanged(inputTyped)
    }
}

@Composable
fun HomeScreenContent(
    holidayTitle: String,
    holidayDescription: String,
    navHostController: NavHostController,
    searchValue: Country,
    dropDownExpanded: Boolean,
    dropDownOptions: List<Country>,
    onDropdownDismissRequest: () -> Unit,
    onItemSelected: () -> Unit,
    onSearchChanged: (String) -> Unit
) {
    Box(
        Modifier
            .fillMaxSize()
            .background(BlueLight)
    ) {
        HomeBackground()

        Column(
            Modifier
                .fillMaxWidth()
                .padding(Medium)
        ) {
            AboutIconButton(navHostController)
            VerticalSpacer(space = Medium)
            AutoCompleteSearchBar(
                modifier = Modifier.padding(horizontal = Small),
                value = searchValue,
                onValueChange = { onSearchChanged(it) },
                onDismissRequest = { onDropdownDismissRequest() },
                onItemSelected = { onItemSelected() },
                dropDownExpanded = dropDownExpanded,
                list = dropDownOptions
            )
        }

        Column(
            Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = Medium)
                .padding(bottom = XLarge),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = holidayTitle,
                color = Yellow,
                fontSize = 38.sp,
                fontWeight = FontWeight.Bold
            )
            VerticalSpacer(space = Medium)
            Text(
                text = holidayDescription,
                color = Color.White,
                fontSize = 22.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomePreview() {
    HomeScreen(navHostController = rememberAnimatedNavController())
}