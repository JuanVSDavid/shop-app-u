package co.edu.ucompensar.shop.infraestructure.entrypoint.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import co.edu.ucompensar.shop.infraestructure.entrypoint.navigation.AppScreens
import co.edu.ucompensar.shop.infraestructure.entrypoint.presentation.ui.theme.AccentBlue
import co.edu.ucompensar.shop.infraestructure.entrypoint.presentation.ui.theme.AccentBlueDarker
import co.edu.ucompensar.shop.infraestructure.entrypoint.presentation.ui.theme.BorderGray
import co.edu.ucompensar.shop.infraestructure.entrypoint.presentation.ui.theme.DarkPrimary
import co.edu.ucompensar.shop.infraestructure.entrypoint.presentation.ui.theme.DarkSecondary
import co.edu.ucompensar.shop.infraestructure.entrypoint.presentation.ui.theme.TextPrimary
import co.edu.ucompensar.shop.infraestructure.entrypoint.presentation.ui.theme.TextSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver atrás",
                            tint = TextPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = DarkPrimary
                )
            )
        },
        containerColor = DarkPrimary
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Crear Cuenta",
                color = TextPrimary,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
            )

            Spacer(modifier = Modifier.height(50.dp))

            InputField(label = "Nombre", placeholder = "Tu nombre")
            Spacer(modifier = Modifier.height(20.dp))
            InputField(label = "Apellido", placeholder = "Tu apellido")
            Spacer(modifier = Modifier.height(20.dp))
            InputField(label = "Email", placeholder = "tu@email.com")
            Spacer(modifier = Modifier.height(20.dp))
            PasswordField(label = "Contraseña")
            Spacer(modifier = Modifier.height(20.dp))
            PasswordField(label = "Confirmar Contraseña")

            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = {
                    navController.popBackStack()
                    navController.navigate(AppScreens.ProfileScreen.route)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AccentBlue)
            ) {
                Text(
                    "Crear Cuenta",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextPrimary
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            LoginRedirectText(navController)

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun InputField(label: String, placeholder: String) {
    var text by rememberSaveable { mutableStateOf("") }
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            color = TextPrimary.copy(alpha = 0.8f),
            fontSize = 14.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            placeholder = { Text(text = placeholder, color = TextSecondary) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = DarkSecondary,
                focusedContainerColor = DarkSecondary,
                focusedTextColor = TextPrimary,
                unfocusedTextColor = TextPrimary,
                unfocusedBorderColor = BorderGray,
                focusedBorderColor = AccentBlue
            )
        )
    }
}

@Composable
private fun PasswordField(label: String) {
    var password by rememberSaveable { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            color = TextPrimary.copy(alpha = 0.8f),
            fontSize = 14.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            placeholder = { Text("••••••••", color = TextSecondary) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            singleLine = true,
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image = if (passwordVisible) Icons.Filled.Done else Icons.Filled.Edit
                val description =
                    if (passwordVisible) "Ocultar contraseña" else "Mostrar contraseña"
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, description, tint = TextSecondary)
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = DarkSecondary,
                focusedContainerColor = DarkSecondary,
                focusedTextColor = TextPrimary,
                unfocusedTextColor = TextPrimary,
                unfocusedBorderColor = BorderGray,
                focusedBorderColor = AccentBlue
            )
        )
    }
}

@Composable
private fun LoginRedirectText(navController: NavHostController) {
    val annotatedString = buildAnnotatedString {
        withStyle(style = SpanStyle(color = TextPrimary)) {
            append("¿Ya tienes una cuenta? ")
        }
        pushStringAnnotation(tag = "LOGIN", annotation = "login_route")
        withStyle(style = SpanStyle(color = AccentBlueDarker, fontWeight = FontWeight.SemiBold)) {
            append("Inicia sesión")
        }
        pop()
    }

    Text(
        text = annotatedString,
        modifier = Modifier.clickable {
            navController.navigate(AppScreens.LoginScreen.route)
        }
    )
}

// --- Preview ---
@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    val navController = rememberNavController()
    RegisterScreen(navController)
}