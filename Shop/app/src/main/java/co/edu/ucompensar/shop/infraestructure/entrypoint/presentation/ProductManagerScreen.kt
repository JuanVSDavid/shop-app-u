package co.edu.ucompensar.shop.infraestructure.entrypoint.presentation

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.error
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import co.edu.ucompensar.shop.domain.model.user.entity.Product
import co.edu.ucompensar.shop.domain.model.user.User
import co.edu.ucompensar.shop.domain.usecase.ProductUseCase
import co.edu.ucompensar.shop.infraestructure.entrypoint.presentation.ui.theme.AccentBlue
import co.edu.ucompensar.shop.infraestructure.entrypoint.presentation.ui.theme.BorderGray
import co.edu.ucompensar.shop.infraestructure.entrypoint.presentation.ui.theme.DarkPrimary
import co.edu.ucompensar.shop.infraestructure.entrypoint.presentation.ui.theme.DarkSecondary
import co.edu.ucompensar.shop.infraestructure.entrypoint.presentation.ui.theme.ShopTheme
import co.edu.ucompensar.shop.infraestructure.entrypoint.presentation.ui.theme.TextPrimary
import co.edu.ucompensar.shop.infraestructure.entrypoint.presentation.ui.theme.TextSecondary
import kotlinx.coroutines.launch
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import co.edu.ucompensar.shop.infraestructure.entrypoint.navigation.AppScreens
import co.edu.ucompensar.shop.infraestructure.entrypoint.security.SessionViewModel
import coil.compose.AsyncImage


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductManagerScreen(navController: NavHostController, sessionViewModel: SessionViewModel) {
    val context = LocalContext.current
    val productAdapter = remember { ProductUseCase(context) }
    val scope = rememberCoroutineScope()

    val currentUser by sessionViewModel.currentUser.collectAsState()

    LaunchedEffect(currentUser) {
        if (currentUser == null) {
            navController.navigate(AppScreens.WelcomeScreen.route) {
                popUpTo(0)
            }
        }
    }

    var products by remember { mutableStateOf<List<Product>>(emptyList()) }
    var showProductDialog by remember { mutableStateOf(false) }
    var productToEdit by remember { mutableStateOf<Product?>(null) }
    var dialogError by remember { mutableStateOf<String?>(null) }

    fun reloadProducts() {
        scope.launch {
            products = productAdapter.getAllProductsByUser(currentUser!!)
        }
    }

    LaunchedEffect(Unit) {
        products = productAdapter.getAllProductsByUser(currentUser!!)
    }

    if (showProductDialog) {
        ProductDialog(
            product = productToEdit,
            error = dialogError,
            onDismiss = {
                showProductDialog = false
                dialogError = null
            },
            onConfirm = { updatedProduct ->
                scope.launch {
                    try {
                        if (productToEdit == null) {
                            productAdapter.createProduct(updatedProduct, currentUser!!)
                        } else {
                            productAdapter.updateProduct(updatedProduct, currentUser!!)
                        }
                        showProductDialog = false
                        dialogError = null
                        reloadProducts()
                    } catch (e: IllegalArgumentException) {
                        dialogError = e.message
                    } catch (e: Exception) {
                        dialogError = "Ocurrió un error inesperado."
                        e.printStackTrace()
                    }
                }
            },
            user = currentUser!!,
            saveImage = productAdapter::saveImageToInternalStorage
        )
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Administrar Productos",
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = TextPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = DarkPrimary)
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    productToEdit = null
                    showProductDialog = true
                },
                containerColor = AccentBlue,
                contentColor = TextPrimary,
                shape = RoundedCornerShape(16.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Añadir producto")
            }
        },
        containerColor = DarkPrimary
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(products) { product ->
                ProductAdminItem(
                    product = product,
                    onEditClick = {
                        productToEdit = product
                        showProductDialog = true
                    },
                    onDeleteClick = {
                        scope.launch {
                            productAdapter.deleteProduct(product.id, currentUser!!)
                            reloadProducts()
                        }
                    }
                )
            }
            item {
                EndOfListMessage()
            }
        }
    }
}


@Composable
private fun ProductDialog(
    product: Product?,
    error: String?,
    onDismiss: () -> Unit,
    onConfirm: (Product) -> Unit,
    saveImage: (Uri) -> Uri?,
    user: User
) {
    var name by remember { mutableStateOf(product?.name ?: "") }
    var price by remember { mutableStateOf(product?.price?.toString() ?: "") }
    var description by remember { mutableStateOf(product?.description ?: "") }
    var stock by remember { mutableStateOf(product?.stock?.toString() ?: "") }

    var imageUri by remember {
        mutableStateOf(
            if (product?.image?.isNotEmpty() == true) product.image.toUri() else null
        )
    }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            imageUri = uri
        }
    )

    val isEditing = product != null
    val title = if (isEditing) "Editar Producto" else "Crear Producto"

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = title, color = TextPrimary) },
        text = {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                item {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        AsyncImage(
                            model = imageUri,
                            contentDescription = "Vista previa del producto",
                            modifier = Modifier
                                .size(120.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(DarkSecondary)
                                .border(1.dp, BorderGray, RoundedCornerShape(12.dp)),
                            contentScale = ContentScale.Crop
                        )
                        Button(onClick = {
                            photoPickerLauncher.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                            )
                        }) {
                            Text("Seleccionar Imagen")
                        }
                    }
                }
                item {
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Nombre del producto") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                item {
                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        label = { Text("Descripción") },
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 3
                    )
                }
                item {
                    OutlinedTextField(
                        value = price,
                        onValueChange = { price = it },
                        label = { Text("Precio") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                    )
                }
                item {
                    OutlinedTextField(
                        value = stock,
                        onValueChange = { stock = it },
                        label = { Text("Stock disponible") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }
                item {
                    error?.let {
                        Text(
                            text = it,
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val finalPrice = price.toDoubleOrNull() ?: 0.0
                    val finalStock = stock.toIntOrNull() ?: 0

                    val localImageUri = imageUri?.let {
                        saveImage(it)
                    }

                    val updatedProduct = product?.copy(
                        name = name,
                        price = finalPrice,
                        description = description,
                        stock = finalStock,
                        image = localImageUri?.toString() ?: ""
                    ) ?: Product(
                        id = 0,
                        name = name,
                        price = finalPrice,
                        description = description,
                        stock = finalStock,
                        image = localImageUri?.toString() ?: "",
                        userId = user.id
                    )
                    onConfirm(updatedProduct)
                },
                colors = ButtonDefaults.buttonColors(containerColor = AccentBlue)
            ) {
                Text("Guardar")
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(containerColor = DarkSecondary)
            ) {
                Text("Cancelar")
            }
        },
        containerColor = DarkPrimary
    )
}

@Composable
private fun ProductAdminItem(
    product: Product,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp))
            .background(DarkSecondary)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = product.image,
            contentDescription = product.name,
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(DarkPrimary),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = product.name,
                fontWeight = FontWeight.SemiBold,
                color = TextPrimary,
                fontSize = 16.sp
            )
            Text(
                text = "$${product.price}",
                color = TextSecondary,
                fontSize = 14.sp
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        IconButton(onClick = onEditClick) {
            Icon(Icons.Default.Edit, contentDescription = "Editar", tint = AccentBlue)
        }
        IconButton(onClick = onDeleteClick) {
            Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = Color.Red)
        }
    }
}

@Composable
private fun EndOfListMessage() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 32.dp)
            .clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp))
            .border(
                width = 1.dp,
                color = BorderGray,
                shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
            )
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Notifications,
            contentDescription = null,
            tint = TextSecondary,
            modifier = Modifier.size(48.dp)
        )
        Text(
            text = "No hay más productos",
            fontWeight = FontWeight.Bold,
            color = TextPrimary,
            fontSize = 18.sp
        )
        Text(
            text = "Parece que has llegado al final de la lista.\n¡Añade uno nuevo para empezar a vender!",
            color = TextSecondary,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProductManagerScreenPreview() {
    val sessionViewModel = viewModel<SessionViewModel>()
    ShopTheme {
        ProductManagerScreen(
            navController = rememberNavController(),
            sessionViewModel = sessionViewModel
        )
    }
}