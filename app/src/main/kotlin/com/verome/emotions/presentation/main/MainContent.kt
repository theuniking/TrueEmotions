package com.verome.emotions.presentation.main

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
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
import com.verome.core.ui.theme.BottomSheetShape
import com.verome.core.ui.theme.additionalColors
import com.verome.core.ui.widgets.dialog.alert.ShowAlertDialog
import com.verome.emotions.auth.presentation.login.LoginScreen
import com.verome.emotions.auth.presentation.registration.RegistrationScreen
import com.verome.emotions.home.presentation.emotion.EmotionScreen
import com.verome.emotions.home.presentation.home.HomeScreen
import com.verome.emotions.home.presentation.profile.ProfileScreen
import com.verome.emotions.home.presentation.reflection.ReflectionContent
import com.verome.emotions.home.presentation.tracker.TrackerScreen

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
                    is OpenScreenEvent -> {
                        navController.navigate(value.screen.route)
                    }

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
        sheetBackgroundColor = MaterialTheme.additionalColors.background,
        sheetShape = MaterialTheme.shapes.BottomSheetShape,
    ) {
        NavHost(
            navController = navController,
            startDestination = if (uiState.isAuthorizedUser) Screen.Main.route else Screen.Auth.route,
        ) {
            navigation(
                route = Screen.Auth.route,
                startDestination = Screen.Auth.LogIn.route,
            ) {
                composable(Screen.Auth.LogIn.route) {
                    LoginScreen(viewModel = hiltViewModel())
                }
                composable(Screen.Auth.SignUp.route) {
                    RegistrationScreen(viewModel = hiltViewModel())
                }
            }
            navigation(
                route = Screen.Main.route,
                startDestination = Screen.Main.Home.route,
            ) {
                composable(
                    route = Screen.Main.Home.route,
                ) {
                    HomeScreen(viewModel = hiltViewModel())
                }
                composable(
                    route = Screen.Main.Reflection.route,
                ) {
                    ReflectionContent()
                }
                bottomSheet(
                    route = Screen.BottomSheetScreen.Profile.route,
                ) {
                    ProfileScreen(viewModel = hiltViewModel())
                }
                bottomSheet(
                    route = Screen.BottomSheetScreen.NewEmotion.route,
                ) {
                    EmotionScreen(viewModel = hiltViewModel())
                }
                bottomSheet(
                    route = Screen.BottomSheetScreen.Tracker.route,
                ) {
                    TrackerScreen(viewModel = hiltViewModel())
                }
            }
        }
    }

    ShowAlertDialog(dialogControl = viewModel.messageDialogControl)
}