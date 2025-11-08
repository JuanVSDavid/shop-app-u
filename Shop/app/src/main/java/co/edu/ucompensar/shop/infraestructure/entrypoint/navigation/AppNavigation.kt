package co.edu.ucompensar.shop.infraestructure.entrypoint.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import co.edu.ucompensar.shop.infraestructure.entrypoint.presentation.CartScreen
import co.edu.ucompensar.shop.infraestructure.entrypoint.presentation.LoginScreen
import co.edu.ucompensar.shop.infraestructure.entrypoint.presentation.ProductManagerScreen
import co.edu.ucompensar.shop.infraestructure.entrypoint.presentation.ProfileScreen
import co.edu.ucompensar.shop.infraestructure.entrypoint.presentation.PublicProductListScreen
import co.edu.ucompensar.shop.infraestructure.entrypoint.presentation.RegisterScreen
import co.edu.ucompensar.shop.infraestructure.entrypoint.presentation.WelcomeScreen
import co.edu.ucompensar.shop.infraestructure.entrypoint.presentation.SplashScreen
import co.edu.ucompensar.shop.infraestructure.entrypoint.security.SessionViewModel
import co.edu.ucompensar.shop.infraestructure.entrypoint.util.CartViewModel

@Composable
fun AppNavigation(){
    val navController = rememberNavController()
    val sessionViewModel: SessionViewModel = viewModel()
    val cartViewModel: CartViewModel = viewModel()

    NavHost(navController = navController, startDestination = AppScreens.SplashScreen.route){
        composable(route = AppScreens.SplashScreen.route) {
            SplashScreen(navController)
        }
        composable(route = AppScreens.WelcomeScreen.route) {
            WelcomeScreen(navController)
        }
        composable(route = AppScreens.RegisterScreen.route) {
            RegisterScreen(navController, sessionViewModel)
        }
        composable(route = AppScreens.LoginScreen.route) {
            LoginScreen(navController, sessionViewModel)
        }
        composable(route = AppScreens.ProfileScreen.route) {
            ProfileScreen(navController, sessionViewModel)
        }
        composable(route = AppScreens.ProductManagerScreen.route) {
            ProductManagerScreen(navController, sessionViewModel)
        }
        composable(route = AppScreens.PublicProductListScreen.route) {
            PublicProductListScreen(navController, cartViewModel)
        }
        composable(route = AppScreens.CartScreen.route) {
            CartScreen(navController, cartViewModel)
        }
    }
}