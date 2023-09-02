package com.verome.emotions.auth.presentation.login

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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.verome.auth.R
import com.verome.core.domain.empty
import com.verome.core.domain.localization.string.resolve
import com.verome.core.ui.widgets.input.CommonInputField
import com.verome.core.ui.theme.AppTheme
import com.verome.core.ui.theme.additionalColors
import com.verome.core.ui.theme.bold
import com.verome.core.ui.widgets.button.DefaultButton

@Composable
internal fun LoginContent(
    uiState: LoginUiState,
    controller: LoginController,
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
                    text = "Welcome back,",
                    style = MaterialTheme.typography.h2,
                    color = MaterialTheme.additionalColors.coreWhite,
                )
                Text(
                    text = "Log in",
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
                errorText = uiState.emailError?.resolve() ?: String.empty,
                trailingIconColor = when {
                    uiState.emailError != null -> MaterialTheme.additionalColors.error
                    else -> MaterialTheme.additionalColors.correct
                },
                trailingIconRes = when {
                    uiState.emailError != null -> R.drawable.ic_disapprove
                    else -> null
                },
            )
            Spacer(modifier = Modifier.height(8.dp))
            CommonInputField(
                text = uiState.password,
                onValueChange = controller::onPasswordFieldChange,
                placeholderText = "Your Password",
                errorText = String.empty,
                trailingIconColor = MaterialTheme.additionalColors.primaryIcon,
                keyboardType = KeyboardType.Password,
                visualTransformation = when {
                    uiState.isPasswordVisible -> {
                        VisualTransformation.None
                    }

                    else -> {
                        PasswordVisualTransformation()
                    }
                },
                trailingIconRes = when {
                    uiState.isPasswordVisible -> R.drawable.ic_eye_crossed_out
                    else -> R.drawable.ic_eye
                },
                onClickTrailingIcon = controller::changePasswordVisibility,
            )
            Spacer(modifier = Modifier.weight(1f))
            DefaultButton(
                text = "Log in",
                onClick = controller::onLoginButtonClick,
                modifier = Modifier.fillMaxWidth(),
                enabled = uiState.isLoginEnabled,
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row {
                Text(
                    text = "Not a member yet?",
                    style = MaterialTheme.typography.subtitle1,
                )
                Spacer(modifier = Modifier.width(3.dp))
                Text(
                    text = "Sign Up",
                    modifier = Modifier.clickable { controller.onRegisterButtonClick() },
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
private fun LoginContentPreview() {
    AppTheme {
        class FakeLoginController : LoginController {
            override fun onEmailFieldChange(email: String) = Unit
            override fun onPasswordFieldChange(password: String) = Unit
            override fun changePasswordVisibility() = Unit
            override fun onLoginButtonClick() = Unit
            override fun onRegisterButtonClick() = Unit
        }

        LoginContent(
            uiState = LoginUiState(
                email = "universe@king.com",
                password = "password",
            ),
            controller = FakeLoginController(),
        )
    }
}