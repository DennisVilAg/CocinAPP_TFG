package com.dennisdevs.cocinApp.navigation

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dennisdevs.cocinApp.model.data_sources.SessionManager
import com.dennisdevs.cocinApp.model.repositories.AuthRepository
import com.dennisdevs.cocinApp.ui.EditShopScreen
import com.dennisdevs.cocinApp.ui.FavoriteRecipesScreen
import com.dennisdevs.cocinApp.ui.ForgotPasswordScreen
import com.dennisdevs.cocinApp.ui.LoginScreen
import com.dennisdevs.cocinApp.ui.PantryScreen
import com.dennisdevs.cocinApp.ui.PurchaseHistoryScreen
import com.dennisdevs.cocinApp.ui.RecipeDetailScreen
import com.dennisdevs.cocinApp.ui.RecipeHomeScreen
import com.dennisdevs.cocinApp.ui.RegisterScreen
import com.dennisdevs.cocinApp.ui.ShopListScreen
import com.dennisdevs.cocinApp.ui.TermsConditionsAndPrivacyPolicyScreen
import com.dennisdevs.cocinApp.ui.UserProfileScreen
import com.dennisdevs.cocinApp.ui.WelcomeScreen
import com.dennisdevs.cocinApp.ui.components.SplashScreen
import com.dennisdevs.cocinApp.viewmodel.AuthViewModel
import com.dennisdevs.cocinApp.viewmodel.HistoryShopListViewModel
import com.dennisdevs.cocinApp.viewmodel.IngredientsForPantryViewModel
import com.dennisdevs.cocinApp.viewmodel.RecipeViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun NavigationWrapper() {
    val context = LocalContext.current
    val navController = rememberNavController()
    val currentUser = FirebaseAuth.getInstance().currentUser

    val authRepository = AuthRepository(
        context,
        FirebaseAuth.getInstance(),
        FirebaseFirestore.getInstance()
    )

    //Comprobar si el usuario esta logueado
    val startDestination = if (currentUser != null && SessionManager.isLoggedIn(context)) {
        RecipeHome
    } else {
        Splash
    }

    // Obtén el ViewModel
    val recipeViewModel: RecipeViewModel = viewModel()
    val historyShopListViewModel: HistoryShopListViewModel = viewModel()
    val authViewModel: AuthViewModel = viewModel()

    // Configura el ActivityResultLauncher para Google Sign-In
    val signInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            val idToken = account.idToken ?: return@rememberLauncherForActivityResult

            authRepository.loginWithGoogle(idToken, onSuccess = {
                //Después de un inicio de sesión exitoso, guardamos el estado en SharedPreferences
                SessionManager.setLoggedIn(context, true)

                navController.navigate(RecipeHome) {
                    popUpTo(Login) { inclusive = true }
                }
            }, onError = { error ->
                // Manejar el error
                Log.e("Auth", "Error al iniciar sesión con Google: $error")
            })
        } catch (e: ApiException) {
            Log.e("Auth", "Google Sign-In failed: ${e.message}")
        }
    }

    NavHost(navController = navController, startDestination = startDestination) {
        composable<Splash> {
            SplashScreen(
                navigateToWelcomeScreen = { navController.navigate(Welcome) },
                navigateBack = { navController.popBackStack() }
            )
        }

        composable<Welcome> { WelcomeScreen(navController) }

        composable<Login> {
            LoginScreen(
                navController = navController,
                onGoogleSignInClick = {
                    val signInIntent = authRepository.googleSignInClient.signInIntent
                    signInLauncher.launch(signInIntent)
                }
            )
        }

        composable<Register> { RegisterScreen(navController) }

        composable<ForgotPassword> { ForgotPasswordScreen(navController) }

        composable<RecipeHome> { RecipeHomeScreen(navController = navController) }

        composable<Pantry> { PantryScreen(navController = navController) }

        composable<UserProfile> {
            UserProfileScreen(
                navController, recipeViewModel = recipeViewModel
            )
        }

        composable("UserProfile/{initialScreen}") { backStackEntry ->
            val initialScreen = backStackEntry.arguments?.getString("initialScreen") ?: "Favorites"
            UserProfileScreen(
                navController = navController,
                recipeViewModel = recipeViewModel,
                initialScreen = initialScreen
            )
        }

        composable<RecipeDetail> {
            RecipeDetailScreen(navController, recipeViewModel = recipeViewModel)
        }

        composable<TermsConditionsAndPrivacyPolicy> {
            TermsConditionsAndPrivacyPolicyScreen(navController)
        }

        composable<FavoriteRecipe> {
            FavoriteRecipesScreen(navController = navController, recipeViewModel = recipeViewModel)
        }

        composable<PurchaseHistory> {
            PurchaseHistoryScreen(viewModel = historyShopListViewModel)
        }

        composable<ShopList> {
            val viewModel: IngredientsForPantryViewModel = viewModel()
            ShopListScreen(navController, viewModel)
        }

        composable<EditShopList> {
            val viewModel: IngredientsForPantryViewModel = viewModel()
            EditShopScreen(navController, viewModel)
        }
    }
}