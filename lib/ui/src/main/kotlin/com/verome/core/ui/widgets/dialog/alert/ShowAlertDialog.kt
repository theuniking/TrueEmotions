package com.verome.core.ui.widgets.dialog.alert

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.verome.core.domain.localization.string.VmRes
import com.verome.core.domain.localization.string.resolve
import com.verome.core.domain.message.Message
import com.verome.core.ui.theme.AppTheme
import com.verome.core.ui.theme.additionalColors
import com.verome.core.ui.widgets.dialog.DialogControl
import com.verome.core.ui.widgets.dialog.DialogResult
import com.verome.domain.R

@Composable
fun ShowAlertDialog(dialogControl: DialogControl<Message, DialogResult>) {
    ShowDialog(dialogControl) { data ->
        AlertDialogContent(data, dialogControl)
    }
}

@Composable
private fun <T : Any, R : Any> ShowDialog(
    dialogControl: DialogControl<T, R>,
    dialog: @Composable (data: T) -> Unit,
) {
    val state by dialogControl.stateFlow.collectAsState()
    (state as? DialogControl.State.Shown)?.data?.let { data ->
        dialog(data)
    }
}

@Composable
private fun AlertDialogContent(
    data: Message,
    dialogControl: DialogControl<Message, DialogResult>,
) {
    AlertDialog(
        shape = MaterialTheme.shapes.small,
        title = data.title?.let { text ->
            {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = text.resolve(),
                    color = MaterialTheme.additionalColors.coreBlack,
                    style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Bold),
                )
            }
        },
        text = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = data.description.resolve(),
                color = MaterialTheme.additionalColors.coreBlack,
                style = MaterialTheme.typography.body2,
            )
        },
        confirmButton = {
            data.positiveButtonText?.let { text ->
                Button(
                    modifier = Modifier.padding(end = 8.dp),
                    onClick = { dialogControl.sendResult(DialogResult.Confirm) },
                ) {
                    Text(text = text.resolve())
                }
            }
        },
        dismissButton = {
            data.negativeButtonText?.let { text ->
                Button(
                    onClick = { dialogControl.sendResult(DialogResult.Cancel) },
                ) {
                    Text(text = text.resolve())
                }
            }
        },
        onDismissRequest = { dialogControl.dismiss() },
    )
}

@Preview
@Composable
fun AlertDialogPreview() {
    AppTheme {
        AlertDialogContent(
            data = Message(
                title = VmRes.Str(stringResource(id = R.string.error_title)),
                description = VmRes.Str(stringResource(id = R.string.error_no_internet_connection)),
                negativeButtonText = VmRes.Str(stringResource(id = R.string.common_close)),
            ),
            dialogControl = DialogControl(),
        )
    }
}