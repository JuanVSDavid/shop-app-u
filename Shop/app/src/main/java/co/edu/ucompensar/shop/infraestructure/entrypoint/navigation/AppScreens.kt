package co.edu.ucompensar.shop.infraestructure.entrypoint.navigation

sealed class AppScreens(val route: String) {
    object SplashScreen : AppScreens("splash_screen")
    object WelcomeScreen : AppScreens("welcome_screen")
    object RegisterScreen : AppScreens("register_screen")
    object LoginScreen: AppScreens("login_screen")
    object ProfileScreen: AppScreens("profile_screen")
    object ProductManagerScreen: AppScreens("product_manager_screen")
    object PublicProductListScreen: AppScreens("public_product_list_screen")
    object CartScreen: AppScreens("cart_screen")
}