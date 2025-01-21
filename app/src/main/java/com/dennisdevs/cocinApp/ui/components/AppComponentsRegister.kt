package com.dennisdevs.cocinApp.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RadialGradientShader
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.dennisdevs.cocinApp.R
import com.dennisdevs.cocinApp.navigation.Login
import com.dennisdevs.cocinApp.ui.theme.BackgroundButtonSignUp
import com.dennisdevs.cocinApp.ui.theme.Verde20
import com.dennisdevs.cocinApp.ui.theme.Verde40

@Composable
fun BackgroundRegisterScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        val largeRadialGradient = object : ShaderBrush() {
            override fun createShader(size: Size): Shader {
                val biggerDimension = maxOf(size.height, size.width)
                return RadialGradientShader(
                    colors = listOf(Color(0xFF007A3F), Color(0xFF00522A)),
                    center = size.center,
                    radius = biggerDimension / 2.5f,
                    colorStops = listOf(0.1f, 0.95f)
                )
            }
        }

        Canvas(modifier = Modifier.fillMaxSize()) {
            translate(left = -420f, top = -1270f) {
                drawCircle(Verde20, radius = 150.dp.toPx())
            }
        }
        Canvas(modifier = Modifier.fillMaxSize()) {
            translate(left = 240f, top = -150f) {
                drawCircle(BackgroundButtonSignUp, radius = 250.dp.toPx())
            }
        }
        Canvas(modifier = Modifier.fillMaxSize()) {
            translate(left = -320f, top = -100f) {
                drawCircle(BackgroundButtonSignUp, radius = 250.dp.toPx())
            }
        }
        Canvas(modifier = Modifier.fillMaxSize()) {
            translate(left = 0f, top = 180f) {
                drawCircle(largeRadialGradient, radius = 360.dp.toPx())
            }
        }
        Canvas(modifier = Modifier.fillMaxSize()) {
            translate(left = -280f, top = 1670f) {
                drawCircle(Color.White, radius = 310.dp.toPx())
            }
        }
        Canvas(modifier = Modifier.fillMaxSize()) {
            translate(left = 550f, top = 2000f) {
                drawOval(
                    color = BackgroundButtonSignUp, size = Size(650f, 750f)
                )
            }
        }
    }
}

@Composable
fun MyTermsAndConditions(
    value: String, onTextSelected: (String) -> Unit, onCheckedChange: (Boolean) -> Unit
) {
    val checkedState = remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(56.dp)
            .padding(start = 25.dp, end = 35.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Checkbox(checked = checkedState.value, onCheckedChange = {
            checkedState.value = it
            onCheckedChange(it)
        })
        ClickableTextComponent(value = value, onTextSelected)
    }
}

@Composable
fun ClickableTextComponent(value: String, onTextSelected: (String) -> Unit) {
    val initialText = "He leído y acepto los "
    val termsAndConditionText = "Terminos y Condiciones "
    val andText = "y la  "
    val privacyPolicyText = "Politica de Privacidad"

    val annotatedString = buildAnnotatedString {
        withStyle(style = SpanStyle(color = Color.White, fontSize = 13.sp)) {
            pushStringAnnotation(tag = initialText, annotation = initialText)
            append(initialText)
        }
        withStyle(style = SpanStyle(color = BackgroundButtonSignUp, fontSize = 13.sp)) {
            pushStringAnnotation(tag = termsAndConditionText, annotation = termsAndConditionText)
            append(termsAndConditionText)
        }
        withStyle(style = SpanStyle(color = Color.White, fontSize = 13.sp)) {
            pushStringAnnotation(tag = andText, annotation = andText)
            append(andText)
        }
        withStyle(style = SpanStyle(color = BackgroundButtonSignUp, fontSize = 13.sp)) {
            pushStringAnnotation(tag = privacyPolicyText, annotation = privacyPolicyText)
            append(privacyPolicyText)
        }
    }

    ClickableText(text = annotatedString, onClick = { offset ->
        annotatedString.getStringAnnotations(offset, offset).firstOrNull()?.also { span ->
            if (span.item == termsAndConditionText || (span.item == privacyPolicyText)) {
                onTextSelected(span.item)
            }
        }
    })
}

@Composable
fun MyBottomOptionRegisterScreen(navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Bottom
    ) {
        Text(
            text = stringResource(id = R.string.ya_tienes_cuenta),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 28.dp),
            fontSize = 14.sp,
            fontWeight = FontWeight.W400
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp)
        ) {
            TextButton(
                onClick = { navController.navigate(Login) },
            ) {
                Text(
                    text = stringResource(id = R.string.inicia_sesion),
                    textAlign = TextAlign.Start,
                    textDecoration = TextDecoration.Underline,
                    color = Color.Black,
                    fontSize = 18.sp
                )
            }
        }
    }
}

@Composable
fun WelcomeText() {
    Text(
        text = stringResource(id = R.string.welcome),
        modifier = Modifier.fillMaxWidth(),
        color = Color.White,
        fontWeight = FontWeight.W500,
        fontSize = 34.sp,
        textAlign = TextAlign.Center,
        letterSpacing = 1.sp
    )
}

@Composable
fun MyCustomTextFieldRegister(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    leadingIcon: ImageVector,
    keyboardType: KeyboardType = KeyboardType.Text,
    modifier: Modifier = Modifier,
    imeAction: ImeAction = ImeAction.Next,
    isPasswordField: Boolean = false,
    passwordVisible: MutableState<Boolean> = remember { mutableStateOf(false) },
    keyboardActions: KeyboardActions = KeyboardActions.Default

) {
    TextField(value = value,
        onValueChange = { if (it.length <= 30) onValueChange(it) },
        textStyle = TextStyle(fontSize = 16.sp),
        label = { Text(text = label) },
        leadingIcon = { Icon(imageVector = leadingIcon, contentDescription = null) },
        trailingIcon = if (isPasswordField) {
            {
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
            }
        } else null,
        visualTransformation = if (isPasswordField && !passwordVisible.value) {
            PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        },
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.White,
            focusedContainerColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedLabelColor = Verde40
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType, imeAction = imeAction
        ),
        keyboardActions = keyboardActions,
        singleLine = true,
        modifier = Modifier.width(310.dp),
        shape = RoundedCornerShape(12.dp))
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
fun RegisterButton(
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .width(250.dp)
            .height(45.dp)
            .shadow(22.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = BackgroundButtonSignUp, contentColor = Color.Black
        )
    ) {
        Text(
            text = stringResource(id = R.string.create_account),
            fontSize = 23.sp,
            letterSpacing = 1.sp
        )
    }
}