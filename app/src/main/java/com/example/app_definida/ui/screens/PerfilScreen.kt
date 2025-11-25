package com.example.app_definida.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.app_definida.ui.components.ImagenInteligente
import com.example.app_definida.viewmodel.PerfilViewModel
import com.example.app_definida.viewmodel.UsuarioViewModel
import java.io.File
//@Composable
//fun PerfilScreen(usuarioViewModel: UsuarioViewModel) {
//    // Recolectamos el estado del usuario del ViewModel
//    val usuario by usuarioViewModel.usuario.collectAsState()
//
//    // Cuando el usuario se carga, la UI se actualiza automáticamente
//    Column {
//        if (usuario != null) {
//            Text("Nombre: ${usuario!!.nombre}")
//            Text("Email: ${usuario!!.email}")
//            // ... Muestra el resto de los datos del perfil
//        } else {
//            // Muestra un indicador de carga mientras se busca al usuario
//            CircularProgressIndicator()
//        }
//    }
//}
@Composable
fun PerfilScreen(
    perfilViewModel: PerfilViewModel = viewModel(),
    usuarioViewModel: UsuarioViewModel
) {
    val uriImagen by perfilViewModel.uriImagen.collectAsState()
    val usuario by usuarioViewModel.usuario.collectAsState(initial = null)
    val context = LocalContext.current

    val galeriaLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            perfilViewModel.actualizarUriImagen(uri)
        }
    )

    fun crearUriTemporal(): Uri {
        val imagesDir = File(context.cacheDir, "images")
        imagesDir.mkdirs()
        val file = File(imagesDir, "${System.currentTimeMillis()}_profile.jpg")
        return FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
    }

    var uriTemporal: Uri? = null
    val camaraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { exito ->
            if (exito) {
                perfilViewModel.actualizarUriImagen(uriTemporal)
            }
        }
    )

    val permisoCamaraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                val newUri = crearUriTemporal()
                uriTemporal = newUri
                camaraLauncher.launch(newUri)
            } else {
                Toast.makeText(context, "Permiso de cámara denegado", Toast.LENGTH_SHORT).show()
            }
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        ImagenInteligente(uri = uriImagen)

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = usuario?.nombre ?: "Nombre no disponible",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = usuario?.email ?: "Email no disponible",
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = usuario?.direccion ?: "Dirección no disponible",
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = { galeriaLauncher.launch("image/*") }) {
            Text("Seleccionar desde Galería")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            val permission = Manifest.permission.CAMERA
            val permissionStatus = ContextCompat.checkSelfPermission(context, permission)

            if (permissionStatus == PackageManager.PERMISSION_GRANTED) {
                val newUri = crearUriTemporal()
                uriTemporal = newUri
                camaraLauncher.launch(newUri)
            } else {
                permisoCamaraLauncher.launch(permission)
            }
        }) {
            Text("Tomar Foto con la Cámara")
        }
    }
}