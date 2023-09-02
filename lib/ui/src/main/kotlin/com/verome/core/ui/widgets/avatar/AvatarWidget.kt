package com.verome.core.ui.widgets.avatar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.verome.core.ui.R
import com.verome.core.ui.theme.AppTheme
import com.verome.core.ui.theme.additionalColors

@Composable
fun AvatarWidget(
    model: Any?,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    backgroundColor: Color = MaterialTheme.additionalColors.background,
) {
    Box(
        modifier = modifier
            .size(112.dp)
            .background(
                color = backgroundColor,
                shape = CircleShape,
            )
            .clip(CircleShape)
            .clickable(
                enabled = onClick != null,
                onClick = { onClick?.invoke() },
            ),
        contentAlignment = Alignment.Center,
    ) {
        if (model == null) {
            Icon(
                modifier = Modifier.size(48.dp),
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_camera),
                contentDescription = null,
            )
        } else {
            AsyncImage(
                model = model,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
            )
        }
    }

}

@Preview
@Composable
private fun AvatarWidgetPreview() {
    AppTheme {
        AvatarWidget(
            model = null,
        )
    }
}