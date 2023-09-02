package com.verome.emotions.home.presentation.emotion.content

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.verome.core.ui.extension.defaultShadow
import com.verome.core.ui.theme.AppTheme
import com.verome.core.ui.theme.additionalColors
import com.verome.core.ui.theme.bold
import com.verome.core.ui.widgets.button.DefaultButton
import com.verome.core.ui.widgets.dialog.picker.date.ShowDatePickerDialog
import com.verome.core.ui.widgets.input.CommonInputField
import com.verome.emotions.home.presentation.emotion.EmotionController
import com.verome.emotions.home.presentation.emotion.EmotionUiState
import com.verome.emotions.home.presentation.emotion.content.common.EmotionPreviewExt

@Composable
internal fun NewEmotionContent(uiState: EmotionUiState.Data, controller: EmotionController) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp),
    ) {
        Spacer(modifier = Modifier.height(14.dp))
        Text(
            text = "Action",
            style = MaterialTheme.typography.h4.bold(),
        )
        Spacer(modifier = Modifier.height(8.dp))
        CommonInputField(
            text = uiState.action,
            onValueChange = controller::onActionFieldChange,
            placeholderText = "Type something...",
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = "${uiState.action.length}/100",
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.additionalColors.textTrick,
            modifier = Modifier.align(Alignment.End),
        )
        Text(
            text = "What happened?",
            style = MaterialTheme.typography.h4.bold(),
        )
        Spacer(modifier = Modifier.height(8.dp))
        CommonInputField(
            text = uiState.whatHappened,
            onValueChange = controller::onWhatHappenedFieldChange,
            placeholderText = "Type something...",
            minLines = 3,
            maxLines = 5,
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Action",
            style = MaterialTheme.typography.h4.bold(),
        )
        Spacer(modifier = Modifier.height(8.dp))
        CommonInputField(
            text = uiState.action,
            onValueChange = controller::onActionFieldChange,
            placeholderText = "place, event, etc",
        )
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Date and Time",
                style = MaterialTheme.typography.h4.bold(),
            )
            Spacer(modifier = Modifier.weight(1f))
            DateTag(text = uiState.date, onClick = controller::onDateChangeClick)
            Spacer(modifier = Modifier.width(8.dp))
            // todo: fix
            DateTag(text = "17:35", onClick = controller::onDateChangeClick)
        }
        Spacer(modifier = Modifier.height(17.dp))
        DefaultButton(
            paddingValuesOutside = PaddingValues(horizontal = 34.dp),
            text = "Continue",
            onClick = controller::onContinueButtonClick,
        )
        Spacer(modifier = Modifier.height(28.dp))
    }

    ShowDatePickerDialog(dialogControl = controller.datePickerControl)
}

@Composable
private fun DateTag(
    text: String,
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .defaultShadow(MaterialTheme.shapes.medium),
        backgroundColor = MaterialTheme.additionalColors.coreWhite,
    ) {
        Text(
            text = text,
            modifier = Modifier
                .clip(MaterialTheme.shapes.medium)
                .clickable(onClick = onClick)
                .padding(horizontal = 10.dp, vertical = 8.dp),
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun NewEmotionContentPreview() {
    AppTheme {
        NewEmotionContent(
            uiState = EmotionPreviewExt.uiState,
            controller = EmotionPreviewExt.FakeEmotionController(),
        )
    }
}