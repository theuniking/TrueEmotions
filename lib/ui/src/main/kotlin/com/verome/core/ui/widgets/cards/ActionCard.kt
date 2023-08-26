package com.verome.core.ui.widgets.cards

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.verome.core.ui.theme.AppTheme
import com.verome.core.ui.theme.additionalColors
import com.verome.core.ui.theme.bold

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ActionCard(
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    title: String,
    onClick: () -> Unit,
) {
    Card(
        onClick = onClick,
        backgroundColor = backgroundColor,
        modifier = modifier.width(82.dp).height(101.dp).padding(end = 8.dp),
    ) {
        Column(
            modifier = Modifier
                .padding(start = 8.dp, end = 8.dp, bottom = 16.dp),
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = title,
                style = MaterialTheme.typography.body1.bold(),
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Preview
@Composable
private fun ActionCardPreview() {
    AppTheme {
        ActionCard(
            backgroundColor = MaterialTheme.additionalColors.actionCard1,
            title = "Минута Рефлексии",
            onClick = {},
        )
    }
}