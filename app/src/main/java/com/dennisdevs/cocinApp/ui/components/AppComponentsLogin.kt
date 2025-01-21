package com.dennisdevs.cocinApp.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RadialGradientShader
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.dennisdevs.cocinApp.R
import com.dennisdevs.cocinApp.model.repositories.AuthRepository
import com.dennisdevs.cocinApp.navigation.RecipeHome
import com.dennisdevs.cocinApp.ui.theme.BackgroundButtonOpenSession
import com.dennisdevs.cocinApp.ui.theme.Verde20
import com.dennisdevs.cocinApp.ui.theme.Verde40
import com.dennisdevs.cocinApp.utils.Validation.Companion.isLoginValid
import com.dennisdevs.cocinApp.utils.Validation.Companion.isValidEmail
import com.dennisdevs.cocinApp.viewmodel.AuthViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun BackgroundLoginScreenGradient() {
    val largeRadialGradient = object : ShaderBrush() {
        override fun createShader(size: Size): Shader {
            val biggerDimension = maxOf(size.height, size.width)
            return RadialGradientShader(
                colors = listOf(Color(0xFF007A3F), Color(0xFF00522A)),
                center = size.center,
                radius = biggerDimension / 2.5f,
                colorStops = listOf(0.2f, 0.85f)
            )
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            translate(left = -420f, top = -1270f) {
                drawCircle(Verde20, radius = 150.dp.toPx())
            }
        }
        Canvas(modifier = Modifier.fillMaxSize()) {
            translate(left = 0f, top = 470f) {
                drawCircle(largeRadialGradient, radius = 340.dp.toPx())
            }
        }
    }
}

@Composable
fun MyDivider() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 35.dp, end = 35.dp, bottom = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f), color = Color.White, thickness = 1.dp
        )

        Text(
            modifier = Modifier.padding(8.dp, 0.dp),
            text = stringResource(id = R.string.or),
            fontSize = 18.sp,
            color = Color.White
        )

        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f), color = Color.White, thickness = 1.dp
        )
    }
}

@Composable
fun MyRedirectionButtonUnderline(
    navController: NavHostController,
    route: Any,
    @StringRes id: Int,
) {
    TextButton(onClick = { navController.navigate(route) }) {
        Text(
            text = stringResource(id = id),
            textDecoration = TextDecoration.Underline,
            color = Color.White,
            fontSize = 16.sp
        )
    }
}

@Composable
fun LoginLogo() {
    Image(
        painter = painterResource(id = R.drawable.logo1negro),
        contentDescription = "Logo",
        modifier = Modifier.size(170.dp)
    )
}

@Composable
fun WelcomeTextLogin() {
    Text(
        text = stringResource(id = R.string.welcome),
        color = Color.White,
        fontSize = 38.sp,
        letterSpacing = 1.sp
    )
}

@Composable
fun MyCustomTextFieldLogInEmail(
    value: String,
    onValueChange: (String) -> Unit,
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        textStyle = TextStyle(fontSize = 16.sp),
        label = { Text(text = stringResource(id = R.string.email)) },
        leadingIcon = { Icon(imageVector = Icons.Default.Mail, contentDescription = null) },
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.White,
            focusedContainerColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedLabelColor = Verde40
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email, imeAction = ImeAction.Next
        ),
        singleLine = true,
        //placeholder = { Text(text = "Introduce correo válido") },
        modifier = Modifier
            .width(310.dp)
            .padding(top = 20.dp),
        shape = RoundedCornerShape(12.dp)
    )
}

@Composable
fun MyCustomTextFieldLogInPassword(
    value: String,
    onValueChange: (String) -> Unit,
    passwordVisible: MutableState<Boolean>,
    keyboardActions: KeyboardActions = KeyboardActions.Default
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        textStyle = TextStyle(fontSize = 16.sp),
        label = { Text(text = stringResource(id = R.string.password)) },
        leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = null) },
        trailingIcon = {
            val iconImage = if (passwordVisible.value) {
                Icons.Filled.Visibility
            } else {
                Icons.Filled.VisibilityOff
            }

            val description = if (passwordVisible.value) {
                stringResource(id = R.string.hide_password)
            } else {
                stringResource(id = R.string.show_password)
            }

            IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                Icon(imageVector = iconImage, contentDescription = description)
            }
        },
        visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
        colors = TextFieldDefaults.colors(
            focusedLabelColor = Verde40,
            unfocusedContainerColor = Color.White,
            focusedContainerColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password, imeAction = ImeAction.Done
        ),
        keyboardActions = keyboardActions,
        singleLine = true,
        //placeholder = { Text(text = "Mínimo 8 caracteres") },
        modifier = Modifier.width(310.dp),
        shape = RoundedCornerShape(12.dp),
    )
}

@Composable
fun LoginButton(
    email: String,
    password: String,
    keyboardController: SoftwareKeyboardController?,
    navController: NavHostController,
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    authViewModel: AuthViewModel
) {
    val context = LocalContext.current
    val authRepository =
        AuthRepository(context, FirebaseAuth.getInstance(), FirebaseFirestore.getInstance())

    Button(
        onClick = {
            keyboardController?.hide()

            if (isLoginValid(email, password)) {
                authRepository.loginWithEmailPassword(email, password, onSuccess = {
                    navController.popBackStack()
                    navController.navigate(RecipeHome)
                }, onError = { errorMessage ->
                    scope.launch {
                        val translatedMessage = authRepository.translateFirebaseError(errorMessage)
                        snackbarHostState.showSnackbar(
                            message = translatedMessage, duration = SnackbarDuration.Short
                        )
                    }
                })
            } else {
                if (!isValidEmail(email)) {
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = "Correo electrónico mal formateado.",
                            duration = SnackbarDuration.Short
                        )
                    }
                } else {
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = "Por favor, complete todos los campos.",
                            duration = SnackbarDuration.Short
                        )
                    }
                }
            }
        },
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .width(310.dp)
            .padding(top = 50.dp, bottom = 30.dp)
            .height(43.dp)
            .shadow(22.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = BackgroundButtonOpenSession, contentColor = Color.White
        )
    ) {
        Text(
            text = stringResource(id = R.string.log_in), fontSize = 21.sp, letterSpacing = 1.sp
        )
    }
}

@Composable
fun GoogleSignInButton(
    modifier: Modifier = Modifier, onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .width(80.dp)
            .height(80.dp)
            .padding(10.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(color = Color.White)
            .clickable { onClick() },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_google),
            contentDescription = "Login con Google",
            modifier = Modifier.size(38.dp)
        )
    }
}
