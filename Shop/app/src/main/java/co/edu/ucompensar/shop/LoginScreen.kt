package co.edu.ucompensar.shop

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import co.edu.ucompensar.shop.navigation.AppScreens
import co.edu.ucompensar.shop.ui.theme.AccentBlue
import co.edu.ucompensar.shop.ui.theme.BorderGray
import co.edu.ucompensar.shop.ui.theme.DarkFieldColor
import co.edu.ucompensar.shop.ui.theme.DarkPrimary
import co.edu.ucompensar.shop.ui.theme.TextPrimary
import co.edu.ucompensar.shop.ui.theme.TextSecondary

@Composable
fun LoginScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .background(DarkPrimary)
            .fillMaxSize()
            .systemBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(60.dp))

        Icon(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            tint = AccentBlue,
            modifier = Modifier
                .size(80.dp)
                .border(
                    2.dp,
                    AccentBlue,
                    RoundedCornerShape(50)
                )
                .padding(16.dp)
        )

        Spacer(modifier = Modifier.height(60.dp))

        Text(
            text = "Bienvenido de nuevo",
            color = TextPrimary,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Inicia sesión para continuar.",
            color = TextSecondary,
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(32.dp))

        LoginInputField(
            label = "Email",
            placeholder = "tucorreo@ejemplo.com",
            leadingIcon = Icons.Default.Email
        )
        Spacer(modifier = Modifier.height(16.dp))
        LoginPasswordField()

        Text(
            text = "¿Olvidaste tu contraseña?",
            color = AccentBlue,
            modifier = Modifier
                .align(Alignment.End)
                .padding(vertical = 8.dp),
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                navController.popBackStack()
                navController.navigate(AppScreens.ProfileScreen.route)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = AccentBlue)
        ) {
            Text("Iniciar Sesión", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(32.dp))

        OrDivider()

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SocialLoginButton(
                text = "Google",
                icon = R.drawable.ic_google,
                modifier = Modifier.weight(1f)
            )
            SocialLoginButton(
                text = "Facebook",
                icon = R.drawable.ic_facebook,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        RegisterRedirectText(navController)

        Spacer(modifier = Modifier.height(24.dp))
    }
}


@Composable
private fun LoginInputField(label: String, placeholder: String, leadingIcon: ImageVector) {
    var text by rememberSaveable { mutableStateOf("") }
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            color = TextPrimary.copy(alpha = 0.8f),
            fontSize = 14.sp,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(placeholder) },
            leadingIcon = { Icon(leadingIcon, contentDescription = null, tint = TextSecondary) },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = DarkFieldColor,
                focusedContainerColor = DarkFieldColor,
                focusedTextColor = TextPrimary,
                unfocusedTextColor = TextPrimary,
                unfocusedBorderColor = BorderGray,
                focusedBorderColor = AccentBlue,
                unfocusedLabelColor = TextSecondary,
                focusedLabelColor = TextSecondary,
            )
        )
    }
}

@Composable
private fun LoginPasswordField() {
    var password by rememberSaveable { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    Column {
        Text(
            text = "Contraseña",
            color = TextPrimary.copy(alpha = 0.8f),
            fontSize = 14.sp,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Contraseña") },
            leadingIcon = {
                Icon(
                    Icons.Default.Lock,
                    contentDescription = null,
                    tint = TextSecondary
                )
            },
            trailingIcon = {
                val icon = if (passwordVisible) Icons.Filled.Done else Icons.Filled.Edit
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(icon, contentDescription = "Toggle visibility", tint = TextSecondary)
                }
            },
            singleLine = true,
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = DarkFieldColor,
                focusedContainerColor = DarkFieldColor,
                focusedTextColor = TextPrimary,
                unfocusedTextColor = TextPrimary,
                unfocusedBorderColor = BorderGray,
                focusedBorderColor = AccentBlue,
                unfocusedLabelColor = TextSecondary,
                focusedLabelColor = TextSecondary,
            )
        )
    }
}

@Composable
private fun OrDivider() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Divider(modifier = Modifier.weight(1f), color = BorderGray)
        Text(
            text = "o",
            color = TextSecondary,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Divider(modifier = Modifier.weight(1f), color = BorderGray)
    }
}

@Composable
private fun SocialLoginButton(text: String, icon: Int, modifier: Modifier = Modifier) {
    OutlinedButton(
        onClick = { },
        modifier = modifier.height(50.dp),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
        border = ButtonDefaults.outlinedButtonBorder.copy(
            width = 1.dp,
            brush = androidx.compose.ui.graphics.SolidColor(BorderGray)
        )
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = "$text logo",
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text, color = TextPrimary)
    }
}

@Composable
private fun RegisterRedirectText(navController: NavHostController) {
    val annotatedString = buildAnnotatedString {
        withStyle(style = SpanStyle(color = TextSecondary)) {
            append("¿No tienes una cuenta? ")
        }
        withStyle(style = SpanStyle(color = AccentBlue, fontWeight = FontWeight.Bold)) {
            append("Regístrate")
        }
    }
    Text(
        text = annotatedString,
        modifier = Modifier.clickable {
            navController.navigate(AppScreens.RegisterScreen.route)
        }
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF1A1F29)
@Composable
fun LoginScreenPreview() {
    val navController = rememberNavController()
    LoginScreen(navController)
}