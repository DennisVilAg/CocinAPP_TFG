package com.dennisdevs.cocinApp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.dennisdevs.cocinApp.R
import com.dennisdevs.cocinApp.ui.components.ButtonBack
import com.dennisdevs.cocinApp.ui.theme.tertiaryContainerLight

@Composable
fun TermsConditionsAndPrivacyPolicyScreen(navController: NavHostController) {
    val termsAndConditions = buildAnnotatedString {
        append("Última actualización: 07/01/2025\n\n")

        append("Bienvenido a CocinApp la aplicación. Al acceder o utilizar nuestra aplicación, aceptas cumplir con estos Términos y Condiciones. Si no estás de acuerdo con estos términos, te pedimos que no utilices la aplicación.\n\n")

        append("1. Aceptación de los Términos\n")
        append("Al utilizar esta aplicación, aceptas estar sujeto a estos Términos y Condiciones, a nuestra Política de Privacidad y a cualquier otra política que podamos proporcionar. Nos reservamos el derecho de modificar o actualizar estos términos en cualquier momento. Las actualizaciones se publicarán en esta página y serán efectivas inmediatamente.\n\n")

        append("2. Uso de la Aplicación\n")
        append("Puedes usar nuestra aplicación únicamente para fines legales y de acuerdo con estos términos. Aceptas no utilizar la aplicación para:\n")
        append("- Violar cualquier ley aplicable.\n")
        append("- Realizar actividades fraudulentas o maliciosas.\n")
        append("- Interferir o interrumpir el funcionamiento de la aplicación.\n\n")

        append("3. Registro y Cuenta de Usuario\n")
        append("Para acceder a algunas funciones de la aplicación, se te puede pedir que crees una cuenta. Al hacerlo, te comprometes a proporcionar información precisa y completa. Eres responsable de mantener la confidencialidad de tu cuenta y de todas las actividades realizadas en ella.\n\n")

        append("4. Propiedad Intelectual\n")
        append("El contenido de la aplicación, incluyendo pero no limitado a texto, imágenes, logotipos, gráficos y software, está protegido por derechos de autor y otras leyes de propiedad intelectual. No se permite la reproducción, distribución o modificación sin el consentimiento expreso de [Nombre de la Empresa].\n\n")

        append("5. Privacidad\n")
        append("Tu privacidad es importante para nosotros. Por favor, revisa nuestra [Política de Privacidad] para comprender cómo recopilamos, usamos y protegemos tu información personal.\n\n")

        append("6. Terminación del Servicio\n")
        append("Nos reservamos el derecho de suspender o cancelar tu acceso a la aplicación en cualquier momento y por cualquier motivo, incluyendo pero no limitado a violaciones de estos Términos y Condiciones.\n\n")

        append("7. Exención de Responsabilidad\n")
        append("La aplicación se proporciona \"tal cual\" y \"según disponibilidad\". No garantizamos que la aplicación esté libre de errores o interrupciones, ni que el contenido sea preciso o esté completo. En ningún caso seremos responsables de daños directos, indirectos, incidentales o consecuentes que puedan surgir del uso o incapacidad para usar la aplicación.\n\n")

        append("8. Modificaciones a los Términos\n")
        append("Podemos actualizar estos Términos y Condiciones de vez en cuando. Cualquier cambio se reflejará en esta página y será efectivo inmediatamente después de su publicación. Te recomendamos que revises regularmente esta página para estar al tanto de cualquier cambio.\n\n")

        append("9. Ley Aplicable\n")
        append("Estos Términos y Condiciones se rigen por las leyes del país o estado en el que operamos, y cualquier disputa relacionada con los mismos se resolverá en los tribunales competentes de dicha jurisdicción.\n\n")

        append("10. Contacto\n")
        append("Si tienes alguna pregunta sobre estos Términos y Condiciones, por favor contacta con nosotros a través de correoque@noexiste.com.\n")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = tertiaryContainerLight)
    ) {

        ButtonBack(navController)

        Text(
            text = stringResource(id = R.string.termsAndConditions),
            fontWeight = FontWeight.SemiBold,
            fontSize = 22.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(bottom = 10.dp)
                .fillMaxWidth()
        )

        Text(
            text = termsAndConditions,
            fontSize = 16.sp,
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(30.dp, 15.dp)
        )
    }
}