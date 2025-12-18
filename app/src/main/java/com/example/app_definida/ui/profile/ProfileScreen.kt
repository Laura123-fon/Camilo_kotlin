package com.example.app_definida.ui.profile

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import coil.compose.AsyncImage
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ProfileScreen(
    usuarioViewModel: UsuarioViewModel,
) {
    val userProfile by usuarioViewModel.userProfile.collectAsState()
    val imageUri by usuarioViewModel.uriImagen.collectAsState()
    val context = LocalContext.current

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? -> usuarioViewModel.actualizarUriImagen(uri) }
    )

    var tempUri by remember { mutableStateOf<Uri?>(null) }

    fun createImageUri(context: Context): Uri {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val file = File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir)
        
        return FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider",
            file
        )
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            if (success) {
                usuarioViewModel.actualizarUriImagen(tempUri)
            } else {
                Toast.makeText(context, "No se capturó la foto", Toast.LENGTH_SHORT).show()
            }
        }
    )

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                val uri = createImageUri(context)
                tempUri = uri
                cameraLauncher.launch(uri)
            } else {
                Toast.makeText(context, "Permiso de cámara denegado", Toast.LENGTH_SHORT).show()
            }
        }
    )

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
                .clickable { galleryLauncher.launch("image/*") },
            contentAlignment = Alignment.Center
        ) {
            if (imageUri == null) {
                Icon(Icons.Default.Person, contentDescription = "Foto de perfil", modifier = Modifier.size(80.dp), tint = MaterialTheme.colorScheme.primary)
            } else {
                AsyncImage(model = imageUri, contentDescription = "Foto de perfil", modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
            }
        }

        Spacer(Modifier.height(16.dp))

        Text(
            text = "${userProfile?.nombre ?: ""} ${userProfile?.apellido ?: ""}".trim().ifEmpty { "Usuario" },
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Text(text = userProfile?.email ?: "Sin correo", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        
        Spacer(Modifier.height(24.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Button(onClick = { galleryLauncher.launch("image/*") }) { Text("Galería") }
            Button(onClick = {
                val permission = Manifest.permission.CAMERA
                if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED) {
                    val uri = createImageUri(context)
                    tempUri = uri
                    cameraLauncher.launch(uri)
                } else {
                    cameraPermissionLauncher.launch(permission)
                }
            }) { Text("Cámara") }
        }
    }
}
