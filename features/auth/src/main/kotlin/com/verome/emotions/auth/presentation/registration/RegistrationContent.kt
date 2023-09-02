package com.verome.emotions.auth.presentation.registration

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.verome.auth.R
import com.verome.core.domain.empty
import com.verome.core.ui.widgets.input.CommonInputField
import com.verome.core.ui.theme.AppTheme
import com.verome.core.ui.theme.additionalColors
import com.verome.core.ui.theme.bold
import com.verome.core.ui.widgets.button.DefaultButton

@Composable
internal fun RegistrationContent(
    uiState: RegistrationUiState,
    controller: RegistrationController,
) {
    Column {
        Box {
            Image(
                modifier = Modifier.fillMaxWidth(),
                imageVector = ImageVector.vectorResource(id = R.drawable.bg_abstract_illustration),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                alignment = Alignment.BottomStart,
            )
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(bottom = 74.dp, start = 16.dp),
            ) {
                Text(
                    text = "Hello, create",
                    style = MaterialTheme.typography.h2,
                    color = MaterialTheme.additionalColors.coreWhite,
                )
                Text(
                    text = "New account",
                    style = MaterialTheme.typography.h1.bold(),
                    color = MaterialTheme.additionalColors.coreWhite,
                    fontFamily = FontFamily.Default,
                )
            }
        }
        Column(
            modifier = Modifier.padding(
                horizontal = 16.dp,
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            CommonInputField(
                text = uiState.email,
                onValueChange = controller::onEmailFieldChange,
                placeholderText = "Your Email",
                errorText = String.empty,
                trailingIconRes = if (uiState.emailError != null) R.drawable.ic_disapprove else null,
            )
            Spacer(modifier = Modifier.height(8.dp))
            CommonInputField(
                text = uiState.name,
                onValueChange = controller::onNameFieldChange,
                placeholderText = "Your Name",
                errorText = String.empty,
            )
            Spacer(modifier = Modifier.height(8.dp))
            CommonInputField(
                text = uiState.password,
                onValueChange = controller::onPasswordFieldChange,
                placeholderText = "Your Password",
                errorText = String.empty,
            )
            Spacer(modifier = Modifier.weight(1f))
            DefaultButton(
                text = "Sign Up",
                onClick = controller::onRegisterButtonClick,
                modifier = Modifier.fillMaxWidth(),
                enabled = uiState.isRegistrationEnabled,
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row {
                Text(
                    text = "Already a member?",
                    style = MaterialTheme.typography.subtitle1,
                )
                Spacer(modifier = Modifier.width(3.dp))
                Text(
                    text = "Log In",
                    modifier = Modifier.clickable { controller.onLoginButtonClick() },
                    style = MaterialTheme.typography.subtitle1.bold(),
                    color = MaterialTheme.additionalColors.btnText,
                )
            }
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun RegistrationContentPreview() {
    AppTheme {
        class FakeRegistrationController : RegistrationController {
            override fun onEmailFieldChange(email: String) = Unit

            override fun onNameFieldChange(name: String) = Unit

            override fun onPasswordFieldChange(password: String) = Unit

            override fun changePasswordVisibility() = Unit

            override fun onRegisterButtonClick() = Unit

            override fun onLoginButtonClick() = Unit
        }

        RegistrationContent(
            uiState = RegistrationUiState(
                email = "universe@king.com",
                name = "Universe",
                password = "password",
            ),
            controller = FakeRegistrationController(),
        )
    }
}