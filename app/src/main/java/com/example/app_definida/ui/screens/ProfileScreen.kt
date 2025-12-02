package com.example.app_definida.ui.screens

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.*
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color // <-- ¡SOLUCIÓN! Asegurar la importación correcta
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import coil.compose.AsyncImage
import com.example.app_definida.ui.theme.VerdeEsmeralda
import com.example.app_definida.viewmodel.AuthViewModel
import com.example.app_definida.viewmodel.UsuarioViewModel
import java.io.File

@Composable
fun ProfileScreen(
    usuarioViewModel: UsuarioViewModel,
    authViewModel: AuthViewModel,
    onLogout: () -> Unit
) {
    val userProfile by usuarioViewModel.userProfile.collectAsState()
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current

    // --- Lógica para la Galería y la Cámara (Restaurada) ---

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? -> imageUri = uri }
    )

    fun createTempUri(context: Context): Uri {
        val imagesDir = File(context.cacheDir, "images")
        imagesDir.mkdirs()
        val file = File(imagesDir, "${System.currentTimeMillis()}_profile.jpg")
        return FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
    }

    var tempUri: Uri? by remember { mutableStateOf(null) }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            if (success) { imageUri = tempUri }
        }
    )

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                val newUri = createTempUri(context)
                tempUri = newUri
                cameraLauncher.launch(newUri)
            } else {
                Toast.makeText(context, "Permiso de cámara denegado", Toast.LENGTH_SHORT).show()
            }
        }
    )

    // --- UI de la Pantalla ---

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Imagen de perfil (con la lógica restaurada)
        Box(
            modifier = Modifier.size(120.dp).clip(CircleShape).border(2.dp, VerdeEsmeralda, CircleShape).clickable { galleryLauncher.launch("image/*") },
            contentAlignment = Alignment.Center
        ) {
            if (imageUri == null) {
                Icon(Icons.Default.Person, contentDescription = "Foto de perfil", modifier = Modifier.size(80.dp), tint = VerdeEsmeralda)
            } else {
                AsyncImage(model = imageUri, contentDescription = "Foto de perfil", modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
            }
        }

        Spacer(Modifier.height(16.dp))

        // Datos del perfil
        Text(
            text = "${userProfile?.nombre ?: ""} ${userProfile?.apellido ?: ""}".trim(),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        // ¡SOLUCIÓN! Usar un color del tema para mayor consistencia
        Text(text = userProfile?.email ?: "", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        
        Spacer(Modifier.height(24.dp))

        // Botones para cámara y galería
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Button(onClick = { galleryLauncher.launch("image/*") }) { Text("Galería") }
            Button(onClick = {
                val permission = Manifest.permission.CAMERA
                if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED) {
                    val newUri = createTempUri(context)
                    tempUri = newUri
                    cameraLauncher.launch(newUri)
                } else {
                    cameraPermissionLauncher.launch(permission)
                }
            }) { Text("Cámara") }
        }

        Spacer(Modifier.weight(1f)) // Empuja el botón de logout hacia abajo

        // Botón de Cerrar Sesión
        Button(onClick = { authViewModel.logout(); onLogout() }, modifier = Modifier.fillMaxWidth()) {
            Text("Cerrar Sesión")
        }
    }
}