package com.example.aplicativocamosa.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.aplicativocamosa.AuthState
import com.example.aplicativocamosa.AuthViewModel


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


    Column (
        modifier = modifier.fillMaxSize(),
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
        BottomNavigationBar(navController = navController)
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {

    Row (
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp),
        verticalAlignment = Alignment.Bottom
    ) {

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
}


