package com.verome.core.ui.input

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.verome.core.ui.theme.AppTheme
import com.verome.core.ui.theme.additionalColors

@Composable
fun CommonInputField(
    modifier: Modifier = Modifier,
    text: String,
    onValueChange: (String) -> Unit,
    trailingIconRes: Any? = null,
    trailingIconColor: Color = MaterialTheme.additionalColors.icon,
    onClickTrailingIcon: (() -> Unit)? = null,
    placeholderText: String? = null,
    shape: Shape = MaterialTheme.shapes.medium.copy(CornerSize(12.dp)),
    maxLines: Int = 1,
    colors: TextFieldColors = TextFieldDefaults.textFieldColors(
        textColor = MaterialTheme.additionalColors.textMain,
        backgroundColor = MaterialTheme.additionalColors.background,
        disabledIndicatorColor = Color.Unspecified,
        errorIndicatorColor = Color.Unspecified,
        focusedIndicatorColor = Color.Unspecified,
        unfocusedIndicatorColor = Color.Unspecified,
        cursorColor = MaterialTheme.additionalColors.coreBlack,
    ),
    keyboardType: KeyboardType = KeyboardType.Text,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    imeAction: ImeAction = ImeAction.Default,
    keyboardActions: KeyboardActions = KeyboardActions(),
) {
    var isFocused by remember { mutableStateOf(false) }

    val trailingIcon = iconGenerating(
        model = trailingIconRes,
        color = trailingIconColor,
        onClick = { onClickTrailingIcon?.invoke() },
    )

    TextField(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = when {
                    isFocused -> MaterialTheme.additionalColors.stroke
                    else -> Color.Transparent
                },
            )
            .onFocusChanged {
                isFocused = it.hasFocus
            },
        value = text,
        onValueChange = onValueChange,
        trailingIcon = trailingIcon,
        placeholder = placeholderText?.let {
            {
                CommonInputDefaultPlaceholder(
                    text = placeholderText,
                    color = MaterialTheme.additionalColors.textTrick,
                )
            }
        },
        shape = shape,
        maxLines = maxLines,
        colors = colors,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
        visualTransformation = if (isFocused || text.isNotEmpty()) visualTransformation else VisualTransformation.None,
        keyboardActions = keyboardActions,
    )
}

@Composable
private fun iconGenerating(
    modifier: Modifier = Modifier,
    model: Any?,
    color: Color = MaterialTheme.additionalColors.coreBlack,
    onClick: (() -> Unit)? = null,
): @Composable (() -> Unit)? {
    if (model == null) return null

    return {
        CommonInputIcon(
            modifier = modifier,
            model = model,
            color = color,
            onClick = onClick,
        )
    }
}

@Composable
private fun CommonInputIcon(
    modifier: Modifier = Modifier,
    model: Any?,
    color: Color = MaterialTheme.additionalColors.coreBlack,
    onClick: (() -> Unit)? = null,
) {
    IconButton(onClick = { onClick?.invoke() }, enabled = onClick != null) {
        AsyncImage(
            model = model,
            contentDescription = null,
            modifier = modifier.size(24.dp),
            colorFilter = ColorFilter.tint(color),
        )
    }
}

@Composable
fun CommonInputDefaultPlaceholder(
    modifier: Modifier = Modifier,
    text: String,
    style: TextStyle = MaterialTheme.typography.body1,
    color: Color = MaterialTheme.additionalColors.coreBlack,
) {
    Text(
        modifier = modifier,
        text = text,
        style = style,
        color = color,
    )
}

@Preview
@Composable
fun CommonInputFieldPreview() {
    var text by remember { mutableStateOf("") }
    AppTheme {
        CommonInputField(
            modifier = Modifier
                .fillMaxWidth(),
            text = text,
            onValueChange = { text = it },
            placeholderText = "Placeholder",
        )
    }
}