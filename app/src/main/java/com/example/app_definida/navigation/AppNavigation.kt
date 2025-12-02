package com.example.app_definida.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.app_definida.data.*
import com.example.app_definida.remote.RetrofitClient
import com.example.app_definida.ui.screens.*
import com.example.app_definida.viewmodel.*
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val context = LocalContext.current

    // --- Dependencias ---
    val tokenManager = TokenManager(context)
    val userManager = UserManager(context)
    val apiService = RetrofitClient.create(context)
    
    val authRepository = AuthRepository(apiService, tokenManager, userManager)
    val authViewModelFactory = AuthViewModelFactory(authRepository)
    val authViewModel: AuthViewModel = viewModel(factory = authViewModelFactory)

    val productRepository = ProductRepository(apiService)
    val productViewModelFactory = ProductViewModelFactory(productRepository)
    val productViewModel: ProductViewModel = viewModel(factory = productViewModelFactory)

    val usuarioViewModelFactory = UsuarioViewModelFactory(userManager)
    val usuarioViewModel: UsuarioViewModel = viewModel(factory = usuarioViewModelFactory)

    val mainViewModel: MainViewModel = viewModel()
    val db = AppDatabase.getDatabase(context)
    val cartDao = db.cartProductDao()
    val cartViewModelFactory = CartViewModelFactory(cartDao)
    val cartViewModel: CartViewModel = viewModel(factory = cartViewModelFactory)

    // --- Lógica de Navegación ---
    LaunchedEffect(key1 = Unit) {
        mainViewModel.navEvents.collectLatest { event ->
            when (event) {
                is NavigationEvent.NavigateTo -> {
                    navController.navigate(event.appRoute.route) {
                        event.popUpToRoute?.let { popUpTo(it.route) { inclusive = event.inclusive } }
                        launchSingleTop = event.singleTop
                    }
                }
                is NavigationEvent.PopBackStack -> navController.popBackStack()
                is NavigationEvent.NavigateUp -> navController.navigateUp()
            }
        }
    }

    val startDestination = AppRoute.Web.route

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(AppRoute.Web.route) {
            WebScreen(
                onContinuar = {
                    mainViewModel.navigateTo(
                        route = AppRoute.Login,
                        popUpToRoute = AppRoute.Web,
                        inclusive = true
                    )
                }
            )
        }

        composable(AppRoute.Login.route) {
            LoginScreen(
                authViewModel = authViewModel,
                onLoginSuccess = {
                    navController.navigate(AppRoute.Main.route) {
                        popUpTo(AppRoute.Login.route) { inclusive = true }
                    }
                },
                onNavigateToRegister = { navController.navigate(AppRoute.Registro.route) }
            )
        }

        composable(AppRoute.Registro.route) {
            RegisterScreen(
                authViewModel = authViewModel,
                onRegisterSuccess = {
                    navController.navigate(AppRoute.Login.route) {
                        popUpTo(AppRoute.Login.route) { inclusive = true }
                    }
                },
                onNavigateToLogin = { navController.popBackStack() }
            )
        }

        composable(AppRoute.Main.route) {
            MainScreen(
                usuarioViewModel = usuarioViewModel,
                mainViewModel = mainViewModel,
                cartViewModel = cartViewModel,
                productViewModel = productViewModel,
                authViewModel = authViewModel,
                onLogout = { 
                    navController.navigate(AppRoute.Login.route) {
                        popUpTo(AppRoute.Main.route) { inclusive = true }
                    }
                }
            )
        }

        composable(AppRoute.Resumen.route) {
            ResumenScreen(usuarioViewModel = usuarioViewModel, mainViewModel = mainViewModel)
        }

        // Usar tu PaymentScreen en lugar de BoletaScreen
        composable(AppRoute.Payment.route) {
            PaymentScreen(
                cartViewModel = cartViewModel,
                onBackClick = { navController.popBackStack() } 
            )
        }
    }
}
