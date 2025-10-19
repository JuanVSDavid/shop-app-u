package co.edu.ucompensar.shop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import co.edu.ucompensar.shop.navigation.AppNavigation
import co.edu.ucompensar.shop.ui.theme.ShopTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShopTheme {
                Surface (modifier = Modifier.fillMaxSize()) {
                    AppNavigation()
                }
            }
        }
    }
}