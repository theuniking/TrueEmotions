package com.verome.emotions.presentation.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.verome.core.ui.activity.ActivityProvider
import com.verome.core.ui.theme.AppTheme
import com.verome.core.ui.theme.additionalColors
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject lateinit var activityProvider: ActivityProvider
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityProvider.attachActivity(activity = this)

//        WindowCompat.setDecorFitsSystemWindows(window, false)

        viewModel.collectMessages()
        val splashScreen = installSplashScreen()

        setContent {
            AppTheme {
                Surface(
                    modifier = Modifier
                        .padding(WindowInsets.navigationBars.asPaddingValues())
                        .fillMaxSize(),
                    color = MaterialTheme.additionalColors.background,
                ) {
                    val uiState by viewModel.uiState.collectAsState()
                    splashScreen.setKeepOnScreenCondition {
                        uiState.isSplashVisible
                    }

                    MainContent(uiState = uiState, viewModel = viewModel)
                }
            }
        }
    }

    override fun onDestroy() {
        activityProvider.detachActivity()
        super.onDestroy()
    }
}