package com.example.aplicativocamosa.pages

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import coil.compose.AsyncImage
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.navigation.NavController

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

import java.io.File
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Objects

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewForm(modifier: Modifier = Modifier, navController: NavController) {

    var mechanicName by remember { mutableStateOf("") }
    var serviceDescription by remember { mutableStateOf("") }
    var partsReplaced by remember { mutableStateOf("") }
    var nextServiceDate by remember { mutableStateOf("") }
    var clientName by remember { mutableStateOf("") }
    var selectedVehicleType by remember { mutableStateOf("") }
    var maintenanceDate by remember { mutableStateOf("") }
    var showDatePicker by remember { mutableStateOf(false) }
    var showNextServiceDatePicker by remember { mutableStateOf(false) }
    val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    var licensePlate by remember { mutableStateOf("") }
    var mileage by remember { mutableStateOf("") }
    var maintenanceType by remember { mutableStateOf("") }

    val datePickerState = rememberDatePickerState()
    val nextServiceDatePickerState = rememberDatePickerState()

    var expanded by remember { mutableStateOf(false) }

    val vehicleTypes = listOf(
        "Camión de carga",
        "Tractocamión",
        "Camión volqueta",
        "Camión cisterna",
        "Camión frigorífico",
        "Camión grúa"
    )

    val lazyListState = rememberLazyListState()

   // @OptIn(ExperimentalMaterial3Api::class)
   // @Composable
   // fun DropDown(){

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        state = lazyListState,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        item {

            Text(text = "Formulario de Mantenimiento", fontSize = 24.sp)

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = mechanicName,
                onValueChange = { mechanicName = it },
                label = { Text("Nombre del Mecánico") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))


            OutlinedTextField(
                value = clientName,
                onValueChange = { clientName = it },
                label = { Text("Nombre del Cliente") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = serviceDescription,
                onValueChange = { serviceDescription = it },
                label = { Text("Descripción del Servicio") },
                modifier = Modifier.fillMaxWidth(),
                maxLines = 3
            )

            Spacer(modifier = Modifier.height(16.dp))



            Text("Tipo de Vehículo", fontSize = 16.sp)
            vehicleTypes.forEach { vehicleType ->
                Row(
                   verticalAlignment = Alignment.CenterVertically
               ) {
                   RadioButton(
                        selected = selectedVehicleType == vehicleType,
                        onClick = { selectedVehicleType = vehicleType }
                    )
                    Text(vehicleType)
                }
             }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = maintenanceDate,
                onValueChange = { },
                label = { Text("Fecha de Mantenimiento") },
                modifier = Modifier.fillMaxWidth(),
                readOnly = true,
                trailingIcon = {
                    IconButton(onClick = { showDatePicker = true }) {
                        Icon(Icons.Default.DateRange, contentDescription = "Seleccione Fecha")
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = licensePlate,
                onValueChange = { licensePlate = it },
                label = { Text("Placa del Vehículo") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = mileage,
                onValueChange = { mileage = it },
                label = { Text("Kilometraje") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = partsReplaced,
                onValueChange = { partsReplaced = it },
                label = { Text("Piezas Reemplazadas") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = maintenanceType,
                onValueChange = { maintenanceType = it },
                label = { Text("Tipo de Mantenimiento") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = nextServiceDate,
                onValueChange = { },
                label = { Text("Fecha del Próximo Servicio") },
                modifier = Modifier.fillMaxWidth(),
                readOnly = true,
                trailingIcon = {
                    IconButton(onClick = { showNextServiceDatePicker = true }) {
                        Icon(
                            Icons.Default.DateRange,
                            contentDescription = "Seleccione Próxima Fecha"
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            ImageCaptureFromCamera()

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Button(onClick = {
                    // Handle form submission
                }) {
                    Text(text = "Enviar")
                }

                Button(onClick = {
                    navController.popBackStack()
                }) {
                    Text(text = "Volver")
                }
            }
        }
    }

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    showDatePicker = false
                    datePickerState.selectedDateMillis?.let { millis ->
                        val localDate = LocalDate.ofEpochDay(millis / 86400000)
                        maintenanceDate = localDate.format(dateFormatter)
                    }
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Cancelar")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    if (showNextServiceDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showNextServiceDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    showNextServiceDatePicker = false
                    nextServiceDatePickerState.selectedDateMillis?.let { millis ->
                        val localDate = LocalDate.ofEpochDay(millis / 86400000)
                        nextServiceDate = localDate.format(dateFormatter)
                    }
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showNextServiceDatePicker = false }) {
                    Text("Cancelar")
                }
            }
        ) {
            DatePicker(state = nextServiceDatePickerState)

        }
    }
}


@Composable
fun ImageCaptureFromCamera() {
    val context = LocalContext.current
    val imageFiles = remember { mutableStateListOf<Pair<Uri, String>>() }
    val file = context.createImageFile()
    val uri = FileProvider.getUriForFile(
        Objects.requireNonNull(context),
        context.packageName + ".provider", file
    )

    val cameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) {
                imageFiles.add(Pair(uri, ""))
            }
        }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if (it) {
            Toast.makeText(context, "Permiso Otorgado", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Permiso Denegado", Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Button(onClick = {
            val permissionCheckResult =
                ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)

            if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                cameraLauncher.launch(uri)
            } else {
                permissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }) {
            Text("Tomar Foto")
        }

        imageFiles.forEachIndexed { index, imageFile ->
            Column {
                AsyncImage(
                    model = imageFile.first,
                    contentDescription = null,
                    modifier = Modifier.padding(16.dp, 8.dp)
                )
                OutlinedTextField(
                    value = imageFile.second,
                    onValueChange = { description ->
                        imageFiles[index] = imageFile.copy(second = description)
                    },
                    label = { Text("Descripción de la Foto") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Button(onClick = {
            val permissionCheckResult =
                ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)

            if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                cameraLauncher.launch(uri)
            } else {
                permissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }) {
            Text("+")
        }
    }
}


@SuppressLint("SimpleDateFormat")
fun Context.createImageFile(): File {
        val timeStamp = SimpleDateFormat("yyyy_MM_dd_HH:mm:ss").format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val image = File.createTempFile(
            imageFileName,
            ".jpg",
            externalCacheDir
        )

        return image
    }


@Composable
fun LocationCapture() {
    val context = LocalContext.current
    val mapView = remember { MapView(context) }
    val location = LatLng(-34.0, 151.0) // Example location

    AndroidView({ mapView }) { mapView ->
        mapView.getMapAsync(OnMapReadyCallback { googleMap ->
            googleMap.addMarker(MarkerOptions().position(location).title("Marker in Sydney"))
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(location))
        })
    }
}






