package com.example.ordernow.presentation.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ordernow.presentation.sign_in.GoogleAuthClient
import com.example.ordernow.presentation.sign_in.SignInResult
import com.example.ordernow.presentation.sign_in.SignInScreen
import com.example.ordernow.presentation.sign_in.SignInViewModel
import com.example.ordernow.presentation.ui.theme.OrderNowTheme
import com.example.ordernow.presentation.util.Main
import com.example.ordernow.presentation.util.SignIn
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()
    private val googleAuthClient by lazy {
        GoogleAuthClient(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        splashScreen.setKeepOnScreenCondition {
            mainViewModel.splashScreenVisible.value
        }

        setContent {
            OrderNowTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = SignIn
                    ) {
                        composable<SignIn> {
                            val viewModel = viewModel<SignInViewModel>()
                            val state by viewModel.state.collectAsStateWithLifecycle()

                            LaunchedEffect(key1 = Unit) {
                                if (googleAuthClient.isSignedIn()) {
                                    navController.navigate(Main) {
                                        popUpTo(SignIn) { inclusive = true }
                                    }
                                }
                            }

                            SignInScreen(
                                modifier = Modifier.padding(innerPadding),
                                state = state,
                                onSignInClick = {
                                    viewModel.startLoading()
                                    lifecycleScope.launch {
                                        try {
                                            val result = googleAuthClient.signIn()
                                            viewModel.onSignInResult(result)
                                            if (result.data != null) {
                                                navController.navigate(Main){
                                                    popUpTo(SignIn) { inclusive = true }//esto hace que no pueda volver hacia atras
                                                }
                                            }else{
                                                viewModel.stopLoading()
                                                viewModel.onSignInResult(
                                                    SignInResult(
                                                        data = null,
                                                        errorMessage = result.errorMessage
                                                    )
                                                )
                                            }

                                        } catch (e: Exception) {
                                            viewModel.stopLoading()
                                            viewModel.onSignInResult(
                                                SignInResult(
                                                    data = null,
                                                    errorMessage = "Error inesperado: ${e.message}"
                                                )
                                            )
                                        }
                                    }
                                }
                            )
                        }
                        composable<Main> {
                            MainScreen(
                                modifier = Modifier.padding(innerPadding),
                                googleAuthClient = googleAuthClient,
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}

