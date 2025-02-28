package com.example.aplicativocamosa.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.ui.res.painterResource
import com.example.aplicativocamosa.R
import androidx.compose.material3.Button
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.aplicativocamosa.AuthState
import com.example.aplicativocamosa.AuthViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(
    modifier: Modifier = Modifier,
    navController: NavController,
    authViewModel: AuthViewModel
) {
    val authState = authViewModel.authState.observeAsState()

    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.Unauthenticated -> navController.navigate("login")
            else -> Unit
        }
    }

    // Create a scroll behavior for the collapsing top app bar
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Image(
                            painter = painterResource(id = R.drawable.large_bg),
                            contentDescription = "CAMOSA BG",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp),
                            contentScale = ContentScale.Crop //Use Crop instead of Fit
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("home")  }) {
                        Icon(
                            painterResource(id = R.drawable.ic_home),
                            contentDescription = "Menu"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* Handle action icon click */ }) {
                        Icon(
                            painterResource(id = R.drawable.ic_settings),
                            contentDescription = "Settings"
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) { innerPadding ->


    Column (
        modifier = modifier
            .fillMaxSize()
            .padding(innerPadding)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment= Alignment.CenterHorizontally
    ) {
        Text(text = "Pagina de Inicio", fontSize = 32.sp)

        TextButton(onClick =  {
            authViewModel.signout()
        }) {
            Text(text = "Cerrar Sesión")
        }

        Button(onClick =  {
            navController.navigate("newform")
        }){
            Text(text = "Ir a Formulario")
        }
        // Add some content to make scrolling possible
        repeat(20) {
            Text(
                text = "Item $it",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                )
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
        NavigationBar {
            NavigationBarItem(
                icon = {
                    Icon(
                        painterResource(id = R.drawable.ic_home),
                        contentDescription = "Home"
                    )
                },
                label = { Text("Inicio") },
                selected = false,
                onClick = { navController.navigate("home") }
            )
            NavigationBarItem(
                icon = {
                    Icon(
                        painterResource(id = R.drawable.ic_profile),
                        contentDescription = "Profile"
                    )
                },
                label = { Text("Perfil") },
                selected = false,
                onClick = { navController.navigate("profile") }
            )
            NavigationBarItem(
                icon = {
                    Icon(
                        painterResource(id = R.drawable.ic_forms),
                        contentDescription = "Forms"
                    )
                },
                label = { Text("Formulario") },
                selected = false,
                onClick = { navController.navigate("newform") }
            )
            NavigationBarItem(
                icon = {
                    Icon(
                        painterResource(id = R.drawable.ic_settings),
                        contentDescription = "Settings"
                    )
                },
                label = { Text("Opción") },
                selected = false,
                onClick = { navController.navigate("settings") }
            )
        }
    }


