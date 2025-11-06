package co.edu.ucompensar.shop.infraestructure.entrypoint.navigation

sealed class AppScreens(val route: String) {
    object SplashScreen : AppScreens("splash_screen")
    object WelcomeScreen : AppScreens("welcome_screen")
    object RegisterScreen : AppScreens("register_screen")
    object LoginScreen: AppScreens("login_screen")
    object ProfileScreen: AppScreens("profile_screen")
}