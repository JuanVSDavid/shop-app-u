package co.edu.ucompensar.shop.infraestructure.entrypoint.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import co.edu.ucompensar.shop.R
import co.edu.ucompensar.shop.infraestructure.entrypoint.navigation.AppScreens
import co.edu.ucompensar.shop.infraestructure.entrypoint.presentation.ui.theme.AccentBlue
import co.edu.ucompensar.shop.infraestructure.entrypoint.presentation.ui.theme.AccentBlueDarker
import co.edu.ucompensar.shop.infraestructure.entrypoint.presentation.ui.theme.DarkPrimary
import co.edu.ucompensar.shop.infraestructure.entrypoint.presentation.ui.theme.TextSecondary
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController){
    LaunchedEffect(key1 = true) {
        delay(5000)
        navController.popBackStack()
        navController.navigate(AppScreens.WelcomeScreen.route)
    }

    Splash()
}

@Composable
fun Splash(){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkPrimary),
        contentAlignment = Alignment.Center
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ){
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                Modifier.size(150.dp, 150.dp),
                colorFilter = ColorFilter.tint(TextSecondary)
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text("Todo lo que necesitas, a un clic", color = TextSecondary)
        }

        LinearProgressIndicator(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(horizontal = 40.dp, vertical = 50.dp)
                .height(10.dp)
            ,
            color = AccentBlue,
            trackColor = AccentBlueDarker
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview(){
    Splash()
}