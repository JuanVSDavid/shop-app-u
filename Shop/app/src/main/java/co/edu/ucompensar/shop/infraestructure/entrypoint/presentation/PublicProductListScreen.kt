package co.edu.ucompensar.shop.infraestructure.entrypoint.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.values
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import co.edu.ucompensar.shop.domain.model.user.entity.Product
import co.edu.ucompensar.shop.domain.usecase.ProductUseCase
import co.edu.ucompensar.shop.infraestructure.entrypoint.navigation.AppScreens
import co.edu.ucompensar.shop.infraestructure.entrypoint.presentation.ui.theme.AccentBlue
import co.edu.ucompensar.shop.infraestructure.entrypoint.presentation.ui.theme.BorderGray
import co.edu.ucompensar.shop.infraestructure.entrypoint.presentation.ui.theme.DarkPrimary
import co.edu.ucompensar.shop.infraestructure.entrypoint.presentation.ui.theme.DarkSecondary
import co.edu.ucompensar.shop.infraestructure.entrypoint.presentation.ui.theme.ShopTheme
import co.edu.ucompensar.shop.infraestructure.entrypoint.presentation.ui.theme.TextPrimary
import co.edu.ucompensar.shop.infraestructure.entrypoint.presentation.ui.theme.TextSecondary
import co.edu.ucompensar.shop.infraestructure.entrypoint.util.CartViewModel
import coil.compose.AsyncImage

@Composable
fun PublicProductListScreen(navController: NavHostController, cartViewModel: CartViewModel){
    val context = LocalContext.current
    val productAdapter = remember { ProductUseCase(context) }

    var products by remember { mutableStateOf<List<Product>>(emptyList()) }

    LaunchedEffect(Unit) {
        products = productAdapter.getAllProducts()
    }

    Scaffold(
        topBar = { TopBar(navController, cartViewModel) },
        bottomBar = { BottomNavBar(navController) },
        containerColor = DarkPrimary
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            SearchBar()
            FilterChips()
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.padding(horizontal = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(products) { product ->
                    ItemProduct(product, cartViewModel::addProductToCart)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(navController: NavHostController, cartViewModel: CartViewModel) {
    val cartItemsState by cartViewModel.cartItems.collectAsState()
    val itemCount = cartItemsState.values.sumOf { it.quantity }

    CenterAlignedTopAppBar(
        title = { Text("Explorar", fontWeight = FontWeight.Bold, color = TextPrimary) },
        actions = {
            BadgedBox(
                badge = {
                    if (itemCount > 0) {
                        Badge { Text(itemCount.toString()) }
                    }
                },
                modifier = Modifier.padding(end = 8.dp)
            ) {
                IconButton(onClick = { navController.navigate(AppScreens.CartScreen.route) }) {
                    Icon(
                        Icons.Default.ShoppingCart,
                        contentDescription = "Carrito de compras",
                        tint = TextPrimary
                    )
                }
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = DarkPrimary
        )
    )
}

@Composable
private fun SearchBar() {
    var searchQuery by remember { mutableStateOf("") }
    OutlinedTextField(
        value = searchQuery,
        onValueChange = { searchQuery = it },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        placeholder = { Text("Buscar productos...", color = TextSecondary) },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Buscar", tint = TextSecondary) },
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = DarkSecondary,
            focusedContainerColor = DarkSecondary,
            focusedTextColor = TextPrimary,
            unfocusedTextColor = TextPrimary,
            unfocusedBorderColor = BorderGray,
            focusedBorderColor = AccentBlue,
        )
    )
}

@Composable
private fun FilterChips() {
    val filters = listOf("Todos", "Categoría", "Precio", "Popularidad")
    var selectedFilter by remember { mutableStateOf("Todos") }

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(filters) { filter ->
            val isSelected = filter == selectedFilter
            val trailingIcon = if (filter == "Categoría" || filter == "Precio") Icons.Default.ArrowDropDown else null

            FilterChip(
                selected = isSelected,
                onClick = { selectedFilter = filter },
                label = { Text(filter) },
                leadingIcon = if (isSelected) {
                    { Icon(Icons.Default.Done, contentDescription = "Seleccionado", modifier = Modifier.size(FilterChipDefaults.IconSize)) }
                } else {
                    null
                },
                trailingIcon = if (trailingIcon != null) {
                    { Icon(trailingIcon, contentDescription = "Desplegar", modifier = Modifier.size(FilterChipDefaults.IconSize)) }
                } else {
                    null
                },
                colors = FilterChipDefaults.filterChipColors(
                    containerColor = DarkSecondary,
                    labelColor = TextSecondary,
                    selectedContainerColor = AccentBlue,
                    selectedLabelColor = TextPrimary,
                    selectedLeadingIconColor = TextPrimary
                )
            )
        }
    }
}

@Composable
fun ItemProduct(product: Product, onAddToCart: (product: Product) -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = DarkPrimary)
    ) {
        Column {
            AsyncImage(
                model = product.image.toUri(),
                contentDescription = product.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(
                        RoundedCornerShape(
                            topStart = 12.dp,
                            topEnd = 12.dp
                        )
                    )
            )
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = product.name,
                    fontWeight = FontWeight.SemiBold,
                    color = TextPrimary,
                    fontSize = 16.sp
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "$${"%.2f".format(product.price)}",
                        color = TextSecondary,
                        fontSize = 14.sp
                    )
                    IconButton(
                        onClick = { onAddToCart(product) },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = "Añadir al carrito",
                            tint = AccentBlue
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun BottomNavBar(navController: NavHostController) {
    var selectedItem by remember { mutableStateOf(1) }
    val items = listOf("Inicio", "Explorar", "Perfil")
    val icons = listOf(
        Icons.Default.Home,
        Icons.Default.Search,
        Icons.Default.Person
    )
    val selectedIcons = listOf(
        Icons.Filled.Home,
        Icons.Filled.Search,
        Icons.Filled.Person
    )

    val onClickActionsPerIcons = listOf(
        {navController.navigate(AppScreens.PublicProductListScreen.route)},
        {navController.navigate(AppScreens.PublicProductListScreen.route)},
        {navController.navigate(AppScreens.ProfileScreen.route)},
    )

    NavigationBar(
        containerColor = DarkSecondary,
        contentColor = TextPrimary
    ) {
        items.forEachIndexed { index, screen ->
            NavigationBarItem(
                icon = { Icon(if (selectedItem == index) selectedIcons[index] else icons[index], contentDescription = screen) },
                label = { Text(screen) },
                selected = selectedItem == index,
                onClick = { if (selectedItem != index) onClickActionsPerIcons[index]() else selectedItem = index },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = AccentBlue,
                    unselectedIconColor = TextSecondary,
                    selectedTextColor = AccentBlue,
                    unselectedTextColor = TextSecondary,
                    indicatorColor = DarkSecondary
                )
            )
        }
    }
}


@Preview(showBackground = true, backgroundColor = 0xFF101C22)
@Composable
fun PublicProductListScreenPreview(){
    val cartViewModel = viewModel<CartViewModel>()
    ShopTheme {
        PublicProductListScreen(navController = rememberNavController(), cartViewModel)
    }
}