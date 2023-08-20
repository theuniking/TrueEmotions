package com.verome.emotions.presentation.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.bottomSheet
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import com.verome.core.ui.navigation.CloseBottomSheetEvent
import com.verome.core.ui.navigation.NavigateBackEvent
import com.verome.core.ui.navigation.OpenBottomSheetEvent
import com.verome.core.ui.navigation.OpenScreenEvent
import com.verome.core.ui.navigation.Screen
import com.verome.core.ui.widgets.dialog.alert.ShowAlertDialog

@OptIn(ExperimentalMaterialNavigationApi::class)
@Composable
internal fun MainContent(uiState: MainUiState, viewModel: MainViewModel) {
    val bottomSheetNavigator = rememberBottomSheetNavigator()
    val navController = rememberNavController(bottomSheetNavigator)
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(key1 = Unit) {
        viewModel
            .eventBus
            .navigationEvents
            .flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .collect { value ->
                when (value) {
                    is OpenScreenEvent -> navController.navigate(value.screen.route)

                    is NavigateBackEvent -> navController.popBackStack()

                    is OpenBottomSheetEvent -> {
                        if (value.screen is Screen.BottomSheetScreen) {
                            navController.navigate(value.screen.route)
                        }
                    }

                    is CloseBottomSheetEvent -> {
                        if (bottomSheetNavigator.navigatorSheetState.isVisible) {
                            navController.popBackStack()
                        }
                    }

                    else -> Unit
                }
            }
    }

    ModalBottomSheetLayout(
        bottomSheetNavigator = bottomSheetNavigator,
    ) {
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
        ) {
            navigation(
                route = Screen.Auth.route,
                startDestination = Screen.Auth.LogIn.route,
            ) {
                composable(Screen.Auth.LogIn.route) {

                }
                composable(Screen.Auth.SignUp.route) {

                }
            }
            composable(
                route = Screen.Home.route,
            ) {

            }
            bottomSheet(
                route = Screen.BottomSheetScreen.Profile.route,
            ) {
            }
        }
    }

    ShowAlertDialog(dialogControl = viewModel.messageDialogControl)
}