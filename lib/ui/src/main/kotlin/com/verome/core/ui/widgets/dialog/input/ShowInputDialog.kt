package com.verome.core.ui.widgets.dialog.input

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.verome.core.ui.theme.AppTheme
import com.verome.core.ui.theme.bold
import com.verome.core.ui.widgets.button.DefaultButton
import com.verome.core.ui.widgets.dialog.DialogControl
import com.verome.core.ui.widgets.input.CommonInputField

@Composable
fun ShowInputDialog(dialogControl: DialogControl<InputDialogData, String>) {
    ShowDialog(dialogControl = dialogControl) { data ->
        InputDialogContent(data = data, dialogControl = dialogControl)
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
private fun InputDialogContent(
    data: InputDialogData,
    dialogControl: DialogControl<InputDialogData, String>,
) {
    var text by remember { mutableStateOf(data.initialText) }
    AlertDialog(
        shape = MaterialTheme.shapes.medium,
        text = {
            Column {
                Text("Change your ${data.fieldName}", style = MaterialTheme.typography.h3.bold())
                CommonInputField(text = text, onValueChange = { text = it })
            }
        },
        confirmButton = {
            Column {
                DefaultButton(
                    text = "Confirm",
                    onClick = { dialogControl.sendResult(text) },
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        },
        onDismissRequest = { dialogControl.dismiss() },
    )
}

@Preview
@Composable
fun InputDialogPreview() {
    AppTheme {
        InputDialogContent(
            data = InputDialogData(
                "Name",
                "Darya",
            ),
            dialogControl = DialogControl(),
        )
    }
}