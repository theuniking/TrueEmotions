package com.verome.core.ui.widgets.tags

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.verome.core.ui.theme.AppTheme
import com.verome.core.ui.theme.ButtonShape
import com.verome.core.ui.theme.additionalColors

@Composable
fun Tag(tag: String) {
    Text(
        modifier = Modifier
            .padding(end = 4.dp, bottom = 6.dp)
            .clip(MaterialTheme.shapes.ButtonShape)
            .border(
                width = 1.dp,
                color = MaterialTheme.additionalColors.tagBorder,
                shape = MaterialTheme.shapes.ButtonShape,
            )
            .padding(horizontal = 10.dp, vertical = 3.dp),
        text = tag,
        style = MaterialTheme.typography.body2,
    )
}

@Preview
@Composable
private fun TagPreview() {
    AppTheme {
        Tag(tag = "Tag")
    }
}