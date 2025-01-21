package com.dennisdevs.cocinApp.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RadialGradientShader
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.dennisdevs.cocinApp.R
import com.dennisdevs.cocinApp.ui.theme.BackgroundButtonOpenSession
import com.dennisdevs.cocinApp.ui.theme.Verde10
import com.dennisdevs.cocinApp.ui.theme.Verde20
import com.dennisdevs.cocinApp.ui.theme.Verde40

@Composable
fun BackgroundWelcomeScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            translate(left = 0f, top = 800f) {
                drawCircle(Verde10, radius = 340.dp.toPx())
            }
        }
        Canvas(modifier = Modifier.fillMaxSize()) {
            translate(left = 0f, top = 1050f) {
                drawCircle(Verde20, radius = 340.dp.toPx())
            }
        }
        Canvas(modifier = Modifier.fillMaxSize()) {
            translate(left = 0f, top = 1300f) {
                drawCircle(Verde40, radius = 340.dp.toPx())
            }
        }
    }
}

@Composable
fun BackgroundWelcomeScreenGradient() {
    val largeRadialGradient = object : ShaderBrush() {
        override fun createShader(size: Size): Shader {
            val biggerDimension = maxOf(size.height, size.width)
            return RadialGradientShader(
                colors = listOf(Color(0xFF00A354), Color(0xFF00522A)),
                center = size.center,
                radius = biggerDimension / 2.5f,
                colorStops = listOf(0.1f, 0.9f)
            )
        }
    }
    Canvas(modifier = Modifier.fillMaxSize()) {
        translate(left = 0f, top = 820f) {
            drawCircle(largeRadialGradient, radius = 330.dp.toPx())
        }
    }
}

@Composable
fun LogoWelcome() {
    Image(
        painter = painterResource(id = R.drawable.logo1negro),
        contentDescription = "Logo",
        modifier = Modifier
            .height(140.dp)
            .size(170.dp)

    )
    Text(
        text = stringResource(id = R.string.cocin_app),
        fontSize = 38.sp
    )
}

@Composable
fun MyRedirectionButton(
    navController: NavHostController,
    route: Any,
    buttonColors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = BackgroundButtonOpenSession,
        contentColor = Color.White
    ),
    @StringRes id: Int
) {
    Button(
        onClick = { /* */navController.navigate(route) },
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .width(220.dp)
            .height(43.dp)
            .shadow(22.dp),
        colors = buttonColors
    ) {
        Text(
            text = stringResource(id = id),
            fontSize = 21.sp,
            letterSpacing = 1.sp
        )
    }
}