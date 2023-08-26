package com.verome.emotions.home.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.verome.core.ui.theme.AppTheme
import com.verome.core.ui.theme.additionalColors
import com.verome.core.ui.theme.bold
import com.verome.core.ui.widgets.cards.ActionCard
import com.verome.core.ui.widgets.cards.EmotionCard

@Composable
internal fun HomeContent(
    uiState: HomeUiState,
    controller: HomeController,
) {
    LazyColumn {
        item {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp),
            ) {
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
                        onClick = controller::onActionCardMinuteOfReflectionClicked,
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
        items(uiState.history) { emotionCard ->
            EmotionCard(
                modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 12.dp),
                emotion = emotionCard,
            )
        }
        item {
            Spacer(modifier = Modifier.height(74.dp))
        }
    }
}

@Preview(showSystemUi = true)
@Composable
internal fun HomeContentPreview() {
    AppTheme {
        class FakeHomeController : HomeController {
            override fun onActionCardMinuteOfReflectionClicked() = Unit
            override fun initEmotionCardHistory() = Unit
        }
        HomeContent(uiState = HomeUiState(), controller = FakeHomeController())
    }
}