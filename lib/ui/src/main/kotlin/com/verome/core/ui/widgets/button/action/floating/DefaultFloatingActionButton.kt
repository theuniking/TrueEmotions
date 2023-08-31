package com.verome.core.ui.widgets.button.action.floating

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.verome.core.ui.R
import com.verome.core.ui.theme.AppTheme
import com.verome.core.ui.theme.additionalColors

@Composable
fun DefaultFloatingActionButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    icon: ImageVector = ImageVector.vectorResource(R.drawable.ic_add),
    shape: Shape = CircleShape,
    contentColor: Color = MaterialTheme.additionalColors.coreWhite,
) {
    FloatingActionButton(
        onClick = onClick,
        shape = shape,
        modifier = Modifier
            .size(52.dp),
    ) {
        Box(
            modifier = Modifier
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.additionalColors.coreGradientFirst,
                            MaterialTheme.additionalColors.coreGradientSecond,
                        ),
                    ),
                )
                .fillMaxSize()
                .then(modifier),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(26.dp),
                tint = contentColor,
            )
        }
    }
}

@Preview
@Composable
private fun DefaultFloatingActionButtonPreview() {
    AppTheme {
        DefaultFloatingActionButton(
            onClick = {},
        )
    }
}