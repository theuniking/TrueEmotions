package com.verome.emotions.home.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.FabPosition
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.verome.core.ui.extension.defaultShadow
import com.verome.core.ui.theme.AppTheme
import com.verome.core.ui.theme.additionalColors
import com.verome.core.ui.theme.bold
import com.verome.core.ui.widgets.button.action.floating.DefaultFloatingActionButton
import com.verome.core.ui.widgets.cards.ActionCard
import com.verome.core.ui.widgets.cards.EmotionCard
import com.verome.emotions.home.presentation.home.navigation.bottom.BottomNavShape
import com.verome.home.R

@Composable
internal fun HomeContent(
    uiState: HomeUiState,
    controller: HomeController,
) {
    Scaffold(
        backgroundColor = MaterialTheme.additionalColors.background,
        bottomBar = {
            BottomAppBar(
                modifier = Modifier
                    .clip(BottomNavShape())
                    .defaultShadow(BottomNavShape())
                    .height(46.dp),
                backgroundColor = MaterialTheme.additionalColors.coreWhite,
                contentColor = MaterialTheme.additionalColors.primaryIcon,
            ) {
                // todo: fix
                BottomNavigationItem(
                    selected = false,
                    onClick = controller::onTrackerClick,
                    icon = {
                        Row {
                            Spacer(modifier = Modifier.weight(1f))
                            Icon(
                                imageVector = ImageVector.vectorResource(id = R.drawable.ic_tracker),
                                contentDescription = null,
                                tint = MaterialTheme.additionalColors.primaryIcon,
                            )
                            Spacer(modifier = Modifier.weight(2f))
                        }
                    },
                )
                BottomNavigationItem(
                    selected = false,
                    onClick = controller::onProfileClick,
                    icon = {
                        Row {
                            Spacer(modifier = Modifier.weight(2f))
                            Icon(
                                imageVector = ImageVector.vectorResource(id = R.drawable.ic_profile),
                                contentDescription = null,
                                tint = MaterialTheme.additionalColors.primaryIcon,
                            )
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    },
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,
        floatingActionButton = {
            DefaultFloatingActionButton(
                onClick = controller::onNewEmotionClick,
            )
        },
    ) { paddingValues ->
        LazyColumn(
            contentPadding = paddingValues,
        ) {
            item {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp),
                ) {
                    Spacer(modifier = Modifier.height(15.dp))
                    Text(
                        text = "Welcome back, Darya",
                        style = MaterialTheme.typography.h2.bold(),
                    )
                    Text(
                        text = "we wish you a good day",
                        style = MaterialTheme.typography.subtitle2,
                    )
                }
                Spacer(modifier = Modifier.height(14.dp))
                LazyRow {
                    item {
                        Spacer(modifier = Modifier.width(16.dp))
                    }
                    item {
                        ActionCard(
                            backgroundColor = MaterialTheme.additionalColors.actionCard1,
                            title = "Минута Рефлексии",
                            onClick = controller::onActionCardMinuteOfReflectionClick,
                        )
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Your history",
                    style = MaterialTheme.typography.h3.bold(),
                    modifier = Modifier.padding(horizontal = 16.dp),
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
            items(uiState.history) { emotion ->
                EmotionCard(
                    modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 12.dp),
                    emotion = emotion,
                )
            }
            item {
                Spacer(modifier = Modifier.height(74.dp))
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
internal fun HomeContentPreview() {
    AppTheme {
        class FakeHomeController : HomeController {
            override fun onActionCardMinuteOfReflectionClick() = Unit
            override fun initEmotionHistory() = Unit
            override fun onProfileClick() = Unit
            override fun onNewEmotionClick() = Unit
            override fun onTrackerClick() = Unit
        }
        HomeContent(uiState = HomeUiState(), controller = FakeHomeController())
    }
}