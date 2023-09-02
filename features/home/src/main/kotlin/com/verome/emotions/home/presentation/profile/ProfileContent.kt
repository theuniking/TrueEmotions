package com.verome.emotions.home.presentation.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.verome.core.domain.empty
import com.verome.core.domain.localization.string.toVmResStr
import com.verome.core.ui.theme.AppTheme
import com.verome.core.ui.theme.additionalColors
import com.verome.core.ui.theme.semiBold
import com.verome.core.ui.widgets.avatar.AvatarWidget
import com.verome.core.ui.widgets.dialog.DialogControl
import com.verome.core.ui.widgets.dialog.input.InputDialogData
import com.verome.core.ui.widgets.dialog.input.ShowInputDialog
import com.verome.core.ui.widgets.toolbar.BottomSheetToolbar
import com.verome.home.R

@Composable
internal fun ProfileContent(
    uiState: ProfileUiState,
    controller: ProfileController,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 700.dp, max = 700.dp),
    ) {
        BottomSheetToolbar(
            title = "Profile".toVmResStr(),
            onBackClick = controller::onBackClick,
        )
        Spacer(modifier = Modifier.height((12 * 1.2).dp))
        AvatarWidget(
            model = if (uiState is ProfileUiState.Data) uiState.image else String.empty,
            onClick = controller::onAvatarClick,
            modifier = Modifier.align(CenterHorizontally),
        )
        Spacer(modifier = Modifier.heightIn((12 * 1.2).dp))
        ProfilePersonalData(
            name = if (uiState is ProfileUiState.Data) uiState.name else String.empty,
            email = if (uiState is ProfileUiState.Data) uiState.email else String.empty,
            onNameClick = controller::onNameClick,
        )
        Spacer(modifier = Modifier.heightIn((14 * 1.2).dp))
        Divider()
        Spacer(modifier = Modifier.height((20 * 1.2).dp))
        ProfilePreferences()
        Spacer(modifier = Modifier.heightIn((14 * 1.2).dp))
        Divider()
        Spacer(modifier = Modifier.heightIn((12 * 1.2).dp))
        Text(
            text = "Log out",
            modifier = Modifier
                .padding(start = (20 * 1.2).dp)
                .clickable { controller.onLogOutButtonClick() },
            style = MaterialTheme.typography.subtitle1.semiBold(),
            color = MaterialTheme.additionalColors.btnText,
        )
    }

    ShowInputDialog(dialogControl = controller.inputControl)
}

@Composable
private fun ProfilePersonalData(
    name: String?,
    email: String?,
    onNameClick: () -> Unit,
) {
    Column {
        Text(
            modifier = Modifier.padding(start = (20 * 1.2).dp),
            text = "Personal Data",
            style = MaterialTheme.typography.subtitle1.semiBold(),
        )
        Spacer(modifier = Modifier.height((12 * 1.2).dp))
        Row(
            modifier = Modifier.clickable(onClick = onNameClick).padding(start = (20 * 1.2).dp),
        ) {
            Text(
                text = "Name",
                style = MaterialTheme.typography.subtitle1,
                color = MaterialTheme.additionalColors.textTrick,
            )
            Spacer(modifier = Modifier.weight(1f))
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = name ?: String.empty,
                    style = MaterialTheme.typography.subtitle1.semiBold(),
                )
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_arrow_forward),
                    contentDescription = null,
                    tint = MaterialTheme.additionalColors.secondaryIcon,
                    modifier = Modifier.padding(horizontal = (3 * 1.2).dp),
                )
            }
        }
        Spacer(modifier = Modifier.height((12 * 1.2).dp))
        Row(
            modifier = Modifier.padding(start = (20 * 1.2).dp),
        ) {
            Text(
                text = "E-mail",
                style = MaterialTheme.typography.subtitle1,
                color = MaterialTheme.additionalColors.textTrick,
            )
            Spacer(modifier = Modifier.weight(1f))
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = email ?: String.empty,
                    style = MaterialTheme.typography.subtitle1.semiBold(),
                )
                Spacer(modifier = Modifier.width((20 * 1.2).dp))
            }
        }
    }
}

@Composable
private fun ProfilePreferences() {
    Column(
        modifier = Modifier.padding(start = (20 * 1.2).dp),
    ) {
        Text(
            text = "Preferences",
            style = MaterialTheme.typography.subtitle1.semiBold(),
        )
        Spacer(modifier = Modifier.height((12 * 1.2).dp))
        Row {
            Text(
                text = "Notifications",
                style = MaterialTheme.typography.subtitle1,
                color = MaterialTheme.additionalColors.textTrick,
            )
            Spacer(modifier = Modifier.weight(1f))
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "1 time per day",
                    style = MaterialTheme.typography.subtitle1.semiBold(),
                )
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_arrow_forward),
                    contentDescription = null,
                    tint = MaterialTheme.additionalColors.secondaryIcon,
                    modifier = Modifier
                        .padding(horizontal = (3 * 1.2).dp)
                        .size((14 * 1.2).dp),
                )
            }
        }
        Spacer(modifier = Modifier.height((12 * 1.2).dp))
        Row {
            Text(
                text = "Color scheme",
                style = MaterialTheme.typography.subtitle1,
                color = MaterialTheme.additionalColors.textTrick,
            )
            Spacer(modifier = Modifier.weight(1f))
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Blue",
                    style = MaterialTheme.typography.subtitle1.semiBold(),
                )
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_arrow_forward),
                    contentDescription = null,
                    tint = MaterialTheme.additionalColors.secondaryIcon,
                    modifier = Modifier
                        .padding(horizontal = (3 * 1.2).dp)
                        .size((14 * 1.2).dp),
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun ProfileContentPreview() {
    AppTheme {
        class FakeProfileController : ProfileController {
            override fun onBackClick() = Unit
            override fun onLogOutButtonClick() = Unit
            override fun onAvatarClick() = Unit
            override fun onNameClick() = Unit
            override val inputControl = DialogControl<InputDialogData, String>()
        }
        ProfileContent(
            uiState = ProfileUiState.Data(
                name = "Universe",
                email = "universe@king.com",
            ),
            controller = FakeProfileController(),
        )
    }
}