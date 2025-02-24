package com.example.aplicativocamosa.pages

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.aplicativocamosa.AuthState
import com.example.aplicativocamosa.AuthViewModel
import com.example.aplicativocamosa.R

@Composable
fun SocialMediaIcons() {
    val uriHandler = LocalUriHandler.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10 .dp, bottom = 10.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {

        Image(
            painter = painterResource(id = R.drawable.fb_icon),
            contentDescription = "Facebook",
            modifier = Modifier
                .size(45.dp)
                .clickable(onClick = { uriHandler.openUri("https://www.facebook.com/CAMOSAHN") }),
        )
        Image(
            painter = painterResource(id = R.drawable.ig_icon),
            contentDescription = "Instagram",
            modifier = Modifier
                .size(45.dp)
                .clickable(onClick = { uriHandler.openUri("https://www.instagram.com/camosahn") }),

            )
        Image(
            painter = painterResource(id = R.drawable.wa_icon),
            contentDescription = "WhatsApp",
            modifier = Modifier
                .size(45.dp)
                .clickable(onClick = { uriHandler.openUri("https://api.whatsapp.com/send?phone=50497737276") }),
            //API de WhatsApp CAMOSA
        )
        Image(
            painter = painterResource(id = R.drawable.yt_icon),
            contentDescription = "YouTube",
            modifier = Modifier
                .size(45.dp)
                .clickable(onClick = { uriHandler.openUri("https://www.youtube.com/CAMOSAHND") }),

            )
        }
    }

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    authViewModel: AuthViewModel
) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    val authState = authViewModel.authState.observeAsState()
    val context = LocalContext.current

    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.Authenticated -> navController.navigate("home")
            is AuthState.Error -> Toast.makeText(
                context,
                (authState.value as AuthState.Error).message, Toast.LENGTH_SHORT
            ).show()

            else -> Unit
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_camosa1),
            contentDescription = "Login Image",
            modifier = Modifier.size(300.dp)
        )

        Text(text = "Bienvenido de nuevo", fontSize = 20.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(4.dp))

        Text(text = "Accede a tu cuenta")

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(value = email, onValueChange = {
            email = it
        }, label = {
            Text(text = "Correo Electronico")
        })

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val image = if (passwordVisible)
                    Icons.Filled.Check
                else Icons.Filled.Close

                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, "Ver Contraseña")
                }
            },
            //modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                authViewModel.login(email, password)
            },
            enabled = authState.value != AuthState.Loading
        ) {
            Text(text = "Acceder")
        }

        Spacer(modifier = Modifier.height(32.dp))


        Text(text = "Olvidaste la contraseña?", modifier = Modifier.clickable {


        })


        Spacer(modifier = Modifier.height(32.dp))

        Text(text = "Ir hacía")


        SocialMediaIcons()
        Spacer(modifier = Modifier.height(10.dp))
    }
    //Spacer(modifier = Modifier.weight(1f, fill = true))

}


