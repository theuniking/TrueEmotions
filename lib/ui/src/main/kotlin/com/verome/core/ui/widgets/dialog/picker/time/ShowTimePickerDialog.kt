package com.verome.core.ui.widgets.dialog.picker.time

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.google.android.material.timepicker.MaterialTimePicker
import com.verome.core.ui.theme.AppTheme
import com.verome.core.ui.widgets.dialog.DialogControl

@Composable
fun ShowTimePickerDialog(dialogControl: DialogControl<TimePickerDialogData, TimePickerDialogData>) {
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
    data: TimePickerDialogData,
    dialogControl: DialogControl<TimePickerDialogData, TimePickerDialogData>,
) {
    val activity = LocalContext.current as AppCompatActivity
    val picker = MaterialTimePicker.Builder()
        .apply {
            data.selectedHour?.let {
                this.setHour(it)
            }
            data.selectedMinute?.let {
                this.setMinute(it)
            }
        }.build()

    with(picker) {
        addOnPositiveButtonClickListener {
            dialogControl.sendResult(
                result = TimePickerDialogData(
                    selectedHour = picker.hour,
                    selectedMinute = picker.minute,
                ),
            )
        }
        addOnCancelListener {
            dialogControl.dismiss()
        }
        addOnDismissListener {
            dialogControl.dismiss()
        }
        addOnNegativeButtonClickListener {
            dialogControl.dismiss()
        }
        show(activity.supportFragmentManager, this.toString())
    }
}

@Preview
@Composable
fun ShowTimePickerDialogPreview() {
    AppTheme {
        ShowTimePickerDialog(dialogControl = DialogControl())
    }
}