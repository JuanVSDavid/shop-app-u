package co.edu.ucompensar.shop.infraestructure.entrypoint.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import co.edu.ucompensar.shop.infraestructure.entrypoint.presentation.LoginScreen
import co.edu.ucompensar.shop.infraestructure.entrypoint.presentation.ProfileScreen
import co.edu.ucompensar.shop.infraestructure.entrypoint.presentation.RegisterScreen
import co.edu.ucompensar.shop.infraestructure.entrypoint.presentation.WelcomeScreen
import co.edu.ucompensar.shop.infraestructure.entrypoint.presentation.SplashScreen

@Composable
fun AppNavigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppScreens.SplashScreen.route){
        composable(route = AppScreens.SplashScreen.route) {
            SplashScreen(navController)
        }
        composable(route = AppScreens.WelcomeScreen.route) {
            WelcomeScreen(navController)
        }
        composable(route = AppScreens.RegisterScreen.route) {
            RegisterScreen(navController)
        }
        composable(route = AppScreens.LoginScreen.route) {
            LoginScreen(navController)
        }
        composable(route = AppScreens.ProfileScreen.route) {
            ProfileScreen(navController)
        }
    }
}