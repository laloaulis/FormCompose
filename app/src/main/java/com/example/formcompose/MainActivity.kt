package com.example.formcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.scale
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.ui.focus.onFocusChanged
import androidx.navigation.NavController
import androidx.navigation.compose.*
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            var darkMode by remember { mutableStateOf(false) }

            MaterialTheme(
                colorScheme = if (darkMode) darkColorScheme() else lightColorScheme()
            ) {

                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "registro") {
                    composable("registro") {
                        RegistroForm(navController, darkMode) {
                            darkMode = !darkMode
                        }
                    }
                    composable("success") {
                        PantallaExito()
                    }
                }
            }
        }
    }
}

@Composable
fun RegistroForm(
    navController: NavController,
    darkMode: Boolean,
    onToggleDarkMode: () -> Unit
) {

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    var nombre by remember { mutableStateOf("") }
    var edad by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var errorCorreo by remember { mutableStateOf(false) }
    var errorEdad by remember { mutableStateOf(false) }
    var errorPassword by remember { mutableStateOf(false) }

    val formularioValido = !errorCorreo && !errorEdad && !errorPassword &&
            nombre.isNotEmpty() &&
            telefono.isNotEmpty() &&
            password.isNotEmpty()

    val azul = Color(0xFF1976D2)

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onToggleDarkMode() },
                containerColor = Color.Gray,
                contentColor = Color.White
            ) {
                Text(if (darkMode) "🌴" else "🦇")
            }
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Text("Formulario de Registro", style = MaterialTheme.typography.titleLarge)

            // COMPONENTE REUTILIZABLE CON ANIMACIÓN
            @Composable
            fun CampoAnimado(
                value: String,
                onChange: (String) -> Unit,
                label: String,
                isError: Boolean = false,
                supporting: String? = null,
                keyboardType: KeyboardType,
                ime: ImeAction
            ) {
                var focused by remember { mutableStateOf(false) }
                val scale by animateFloatAsState(if (focused) 1.03f else 1f)

                OutlinedTextField(
                    value = value,
                    onValueChange = onChange,
                    label = { Text(label) },
                    isError = isError,
                    supportingText = {
                        if (isError && supporting != null) {
                            Text(supporting)
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = keyboardType,
                        imeAction = ime
                    ),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = azul,
                        focusedLabelColor = azul,
                        cursorColor = azul
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .scale(scale)
                        .onFocusChanged { focused = it.isFocused }
                        .semantics { contentDescription = "Campo $label" }
                )
            }
            git --version
            // CAMPOS
            CampoAnimado(nombre, { nombre = it }, "Nombre",
                keyboardType = KeyboardType.Text, ime = ImeAction.Next)

            CampoAnimado(edad, {
                edad = it
                errorEdad = it.toIntOrNull()?.let { n -> n <= 0 || n > 120 }
                    ?: it.isNotEmpty()
            }, "Edad",
                isError = errorEdad,
                supporting = "Edad inválida",
                keyboardType = KeyboardType.Number,
                ime = ImeAction.Next
            )

            CampoAnimado(correo, {
                correo = it
                errorCorreo = !android.util.Patterns.EMAIL_ADDRESS
                    .matcher(it).matches() && it.isNotEmpty()
            }, "Correo",
                isError = errorCorreo,
                supporting = "Correo inválido",
                keyboardType = KeyboardType.Email,
                ime = ImeAction.Next
            )

            CampoAnimado(telefono, { telefono = it }, "Teléfono",
                keyboardType = KeyboardType.Phone,
                ime = ImeAction.Next
            )

            // CONTRASEÑA (con animación)
            var focused by remember { mutableStateOf(false) }
            val scale by animateFloatAsState(if (focused) 1.03f else 1f)

            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                    errorPassword = it.length < 6 && it.isNotEmpty()
                },
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                isError = errorPassword,
                supportingText = {
                    if (errorPassword) Text("Mínimo 6 caracteres")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = azul,
                    focusedLabelColor = azul,
                    cursorColor = azul
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .scale(scale)
                    .onFocusChanged { focused = it.isFocused }
                    .semantics { contentDescription = "Campo contraseña" }
            )

            // BOTÓN
            Button(
                onClick = {
                    navController.navigate("success")
                    scope.launch {
                        snackbarHostState.showSnackbar("Bienvenido $nombre")
                    }
                },
                enabled = formularioValido,
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = azul,
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
            ) {
                Text("Registrar")
            }
        }
    }
}

@Composable
fun PantallaExito() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Registro exitoso :)", style = MaterialTheme.typography.headlineMedium)
    }
}