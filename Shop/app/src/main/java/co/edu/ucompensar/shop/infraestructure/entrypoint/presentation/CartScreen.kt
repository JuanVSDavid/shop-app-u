package co.edu.ucompensar.shop.infraestructure.entrypoint.presentation

import android.icu.text.NumberFormat
import androidx.compose.animation.core.copy
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.isEmpty
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.values
import androidx.core.net.toUri
import androidx.navigation.NavHostController
import co.edu.ucompensar.shop.domain.model.user.value.CartItem
import co.edu.ucompensar.shop.infraestructure.entrypoint.presentation.ui.theme.AccentBlue
import co.edu.ucompensar.shop.infraestructure.entrypoint.presentation.ui.theme.BorderGray
import co.edu.ucompensar.shop.infraestructure.entrypoint.presentation.ui.theme.DarkPrimary
import co.edu.ucompensar.shop.infraestructure.entrypoint.presentation.ui.theme.DarkSecondary
import co.edu.ucompensar.shop.infraestructure.entrypoint.presentation.ui.theme.TextPrimary
import co.edu.ucompensar.shop.infraestructure.entrypoint.presentation.ui.theme.TextSecondary
import co.edu.ucompensar.shop.infraestructure.entrypoint.util.CartViewModel
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(navController: NavHostController, cartViewModel: CartViewModel) {
    val cartItemsState by cartViewModel.cartItems.collectAsState()
    val cartItems = cartItemsState.values.toList()

    val subtotal = cartItems.sumOf { it.product.price * it.quantity }
    val shippingCost = if (subtotal > 0) 5.0 else 0.0
    val total = subtotal + shippingCost

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mi Carrito", color = TextPrimary, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver", tint = TextPrimary)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = DarkPrimary)
            )
        },
        containerColor = DarkPrimary
    ) { innerPadding ->
        if (cartItems.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text("Tu carrito está vacío", color = TextSecondary, fontSize = 18.sp)
            }
        } else {
            Column(modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()) {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(cartItems, key = { it.product.id }) { cartItem ->
                        CartItemRow(
                            cartItem = cartItem,
                            onQuantityChange = { newQuantity ->
                                cartViewModel.updateProductQuantity(cartItem.product.id, newQuantity)
                            },
                            onRemove = { cartViewModel.removeProductFromCart(cartItem.product.id) }
                        )
                    }
                }

                CheckoutSummary(subtotal, shippingCost, total)
            }
        }
    }
}

@Composable
private fun CartItemRow(cartItem: CartItem, onQuantityChange: (Int) -> Unit, onRemove: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(DarkSecondary, RoundedCornerShape(12.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = cartItem.product.image.toUri(),
            contentDescription = cartItem.product.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(64.dp)
                .clip(androidx.compose.foundation.shape.RoundedCornerShape(8.dp))
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(cartItem.product.name, color = TextPrimary, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
            Text(
                text = formatCurrency(cartItem.product.price),
                color = TextSecondary,
                fontSize = 14.sp
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        QuantitySelector(
            quantity = cartItem.quantity,
            onQuantityChange = onQuantityChange,
            onRemove = onRemove
        )
    }
}

@Composable
private fun QuantitySelector(quantity: Int, onQuantityChange: (Int) -> Unit, onRemove: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        SmallIconButton(icon = Icons.Default.KeyboardArrowLeft, onClick = { onQuantityChange(quantity - 1) })
        Text(
            text = quantity.toString(),
            color = TextPrimary,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
        SmallIconButton(icon = Icons.Default.KeyboardArrowRight, onClick = { onQuantityChange(quantity + 1) })
        IconButton(onClick = onRemove, modifier = Modifier.size(24.dp)) {
            Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = Color.Gray)
        }
    }
}

@Composable
private fun SmallIconButton(icon: androidx.compose.ui.graphics.vector.ImageVector, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(24.dp)
            .clip(CircleShape)
            .background(Color.Gray.copy(alpha = 0.2f))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(icon, contentDescription = null, tint = TextPrimary, modifier = Modifier.size(
            16.dp
        ))
    }
}

@Composable
private fun CheckoutSummary(subtotal: Double, shippingCost: Double, total: Double) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(DarkSecondary)
            .padding(16.dp)
    ) {
        SummaryRow("Subtotal", subtotal)
        Spacer(modifier = Modifier.height(8.dp))
        SummaryRow("Gastos de Envío", shippingCost)
        HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = BorderGray)
        SummaryRow("Total", total, isTotal = true)
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { /* TODO: Navegar al proceso de pago */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = AccentBlue)
        ) {
            Text("Proceder al Pago", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = TextPrimary)
        }
    }
}

@Composable
private fun SummaryRow(label: String, amount: Double, isTotal: Boolean = false) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(
            text = label,
            color = if (isTotal) TextPrimary else TextSecondary,
            fontSize = if (isTotal) 18.sp else 16.sp,
            fontWeight = if (isTotal) FontWeight.Bold else FontWeight.Normal
        )
        Text(
            text = formatCurrency(amount),
            color = if (isTotal) TextPrimary else TextSecondary,
            fontSize = if (isTotal) 18.sp else 16.sp,
            fontWeight = if (isTotal) FontWeight.Bold else FontWeight.Normal
        )
    }
}

private fun formatCurrency(amount: Double): String {
    return NumberFormat.getCurrencyInstance(java.util.Locale("es", "CO")).format(amount)
}