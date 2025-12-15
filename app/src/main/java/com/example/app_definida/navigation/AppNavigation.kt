package com.example.app_definida.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.app_definida.data.local.AppDatabase
import com.example.app_definida.data.local.TokenManager
import com.example.app_definida.data.local.UserManager
import com.example.app_definida.data.remote.RetrofitClient
import com.example.app_definida.data.repository.AuthRepository
import com.example.app_definida.data.repository.ProductRepository
import com.example.app_definida.ui.auth.AuthViewModel
import com.example.app_definida.ui.auth.AuthViewModelFactory
import com.example.app_definida.ui.auth.LoginScreen
import com.example.app_definida.ui.auth.RegisterScreen
import com.example.app_definida.ui.auth.ResumenScreen
import com.example.app_definida.ui.cart.CartScreen
import com.example.app_definida.ui.cart.CartViewModel
import com.example.app_definida.ui.cart.CartViewModelFactory
import com.example.app_definida.ui.checkout.PaymentScreen
import com.example.app_definida.ui.main.BottomBarRoute
import com.example.app_definida.ui.main.MainScreen
import com.example.app_definida.ui.main.MainViewModel
import com.example.app_definida.ui.product.ProductCatalogScreen
import com.example.app_definida.ui.product.ProductViewModel
import com.example.app_definida.ui.product.ProductViewModelFactory
import com.example.app_definida.ui.profile.ProfileScreen
import com.example.app_definida.ui.profile.UsuarioViewModel
import com.example.app_definida.ui.profile.UsuarioViewModelFactory

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val appNavController = rememberNavController()
    val context = LocalContext.current

    // --- Restauramos todas las dependencias ---
    val tokenManager = TokenManager(context)
    val userManager = UserManager(context)
    val apiService = RetrofitClient.create(context)
    
    val authRepository = AuthRepository(apiService, tokenManager, userManager)
    val authViewModel: AuthViewModel = viewModel(factory = AuthViewModelFactory(authRepository))

    val productRepository = ProductRepository(apiService)
    val productViewModel: ProductViewModel = viewModel(factory = ProductViewModelFactory(productRepository))

    val usuarioViewModel: UsuarioViewModel = viewModel(factory = UsuarioViewModelFactory(userManager))
    
    val db = AppDatabase.getDatabase(context)
    val cartDao = db.cartProductDao()
    val cartViewModel: CartViewModel = viewModel(factory = CartViewModelFactory(cartDao))

    NavHost(
        navController = appNavController,
        startDestination = AppRoute.Login.route, // <-- ¡NUEVO PUNTO DE INICIO!
        modifier = modifier
    ) {
        composable(AppRoute.Login.route) {
            LoginScreen(
                authViewModel = authViewModel,
                onLoginSuccess = {
                    appNavController.navigate(AppRoute.Main.route) {
                        popUpTo(AppRoute.Login.route) { inclusive = true }
                    }
                },
                onNavigateToRegister = { appNavController.navigate(AppRoute.Registro.route) }
            )
        }

        composable(AppRoute.Registro.route) {
            RegisterScreen(
                authViewModel = authViewModel,
                onRegisterSuccess = {
                    appNavController.navigate(AppRoute.Resumen.route)
                },
                onNavigateToLogin = { appNavController.popBackStack() }
            )
        }

        composable(AppRoute.Main.route) {
            val bottomNavController = rememberNavController()

            MainScreen(
                navController = bottomNavController,
                onLogout = {
                    authViewModel.logout()
                    appNavController.navigate(AppRoute.Login.route) {
                        popUpTo(AppRoute.Main.route) { inclusive = true }
                    }
                }
            ) { paddingValues ->
                NavHost(
                    navController = bottomNavController,
                    startDestination = BottomBarRoute.Catalog.route,
                    modifier = Modifier.padding(paddingValues)
                ) {
                    composable(BottomBarRoute.Catalog.route) {
                        ProductCatalogScreen(
                            cartViewModel = cartViewModel,
                            productViewModel = productViewModel
                        )
                    }
                    composable(BottomBarRoute.Profile.route) {
                        ProfileScreen(
                            usuarioViewModel = usuarioViewModel
                        )
                    }
                    composable(BottomBarRoute.Cart.route) {
                        CartScreen(
                            cartViewModel = cartViewModel,
                            onGoToCheckout = {
                                appNavController.navigate(AppRoute.Payment.route)
                            }
                        )
                    }
                }
            }
        }

        composable(AppRoute.Resumen.route) {
            ResumenScreen(
                usuarioViewModel = usuarioViewModel,
                onNavigateToLogin = {
                    appNavController.navigate(AppRoute.Login.route) {
                        popUpTo(AppRoute.Resumen.route) { inclusive = true }
                    }
                }
            )
        }

        composable(AppRoute.Payment.route) {
            PaymentScreen(
                cartViewModel = cartViewModel,
                onBackClick = { appNavController.popBackStack() }
            )
        }
    }
}
