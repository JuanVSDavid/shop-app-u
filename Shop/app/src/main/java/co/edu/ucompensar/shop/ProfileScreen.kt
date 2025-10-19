package co.edu.ucompensar.shop

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import co.edu.ucompensar.shop.ui.theme.AccentBlue
import co.edu.ucompensar.shop.ui.theme.DarkFieldColor
import co.edu.ucompensar.shop.ui.theme.DarkPrimary
import co.edu.ucompensar.shop.ui.theme.TextPrimary

private val RedButtonColor = Color(0xFF4C2C3A)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mi Perfil", color = TextPrimary, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = TextPrimary
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* TODO: Mostrar menú de opciones */ }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "Más opciones",
                            tint = TextPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = DarkPrimary)
            )
        },
        containerColor = DarkPrimary
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ProfileHeader()
            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AccentBlue.copy(0.2f))
            ) {
                Text("Editar perfil", fontWeight = FontWeight.SemiBold, color = AccentBlue)
            }
            Spacer(modifier = Modifier.height(32.dp))

            ProfileMenuItem(icon = Icons.AutoMirrored.Outlined.ArrowBack, text = "Mis pedidos")
            HorizontalDivider(thickness = 8.dp, color = DarkPrimary)
            ProfileMenuItem(icon = Icons.Outlined.Home, text = "Direcciones de envío")
            HorizontalDivider(color = DarkPrimary, thickness = 8.dp)
            ProfileMenuItem(icon = Icons.Outlined.Lock, text = "Métodos de pago")
            HorizontalDivider(color = DarkPrimary, thickness = 8.dp)
            ProfileMenuItem(icon = Icons.Outlined.Notifications, text = "Notificaciones")
            HorizontalDivider(color = DarkPrimary, thickness = 8.dp)
            ProfileMenuItem(icon = Icons.Outlined.Info, text = "Ayuda")

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = RedButtonColor)
            ) {
                val colorText = Color(0xFFED4343)
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                    contentDescription = null,
                    tint = colorText
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Cerrar sesión", fontWeight = FontWeight.SemiBold, color = colorText)
            }
        }
    }
}

@Composable
private fun ProfileHeader() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(DarkFieldColor),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Foto de perfil",
                tint = Color.LightGray,
                modifier = Modifier.size(50.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Juan David Ruiz Vargas",
            color = TextPrimary,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "jdruizvargas@ucompensar.edu.co",
            color = Color.Gray,
            fontSize = 14.sp
        )
    }
}

@Composable
private fun ProfileMenuItem(icon: ImageVector, text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp))
            .background(Color(0xFF1A2831))
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = text,
            tint = AccentBlue,
            modifier = Modifier
                .size(40.dp)
                .clip(androidx.compose.foundation.shape.RoundedCornerShape(8.dp))
                .background(AccentBlue.copy(alpha = 0.2f))
                .padding(8.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = text,
            color = TextPrimary,
            fontSize = 16.sp,
            modifier = Modifier.weight(1f)
        )
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = "Ir a $text",
            tint = Color.Gray
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF1A1F29)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen(navController = rememberNavController())
}
