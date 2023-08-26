package com.verome.core.ui.navigation

sealed class Screen(val route: String) {
    data object Auth : Screen(route = "auth") {
        data object LogIn : Screen("log_in")
        data object SignUp : Screen("sign_up")
    }
    data object Main : Screen(route = "main") {
        data object Home : Screen(route = "home")
        data object Reflection : Screen(route = "reflection")

    }
    sealed class BottomSheetScreen(bottomRoute: String) : Screen(route = bottomRoute) {
        data object Profile : BottomSheetScreen("profile")
        data object Tracker : BottomSheetScreen("tracker")
        data object NewEmotion : BottomSheetScreen("new_emotion")
    }
}
