package com.example.plaintext.ui.screens.login

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.plaintext.R
import com.example.plaintext.ui.viewmodel.PreferencesViewModel
import com.example.plaintext.ui.viewmodel.LoginViewModel
import com.example.plaintext.ui.viewmodel.LoginViewState
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.ui.tooling.preview.Preview

data class LoginState(
    val preencher: Boolean,
    val login: String,
    val navigateToSettings: () -> Unit,
    val navigateToList: (name: String) -> Unit,
    val checkCredentials: (login: String, password: String) -> Boolean,
)

@Composable
fun Login_screen(
    navigateToSettings: () -> Unit,
    navigateToList: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    Login_screen_content(
        state = viewModel.loginState,
        onLoginChange = { viewModel.updateLoginText(it) },
        onPasswordChange = { viewModel.updatePasswordText(it) },
        onSaveLoginChange = { viewModel.updateSaveLoginInfo(it) },
        onLoginClick = {
            if (viewModel.checkCredentials()) {
                navigateToList()
            } else {
                Toast.makeText(context, "Login/Senha invalidos", Toast.LENGTH_SHORT).show()
            }
        },
        navigateToSettings = navigateToSettings
    )
}

@Composable
fun Login_screen_content(
    state: LoginViewState,
    onLoginChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSaveLoginChange: (Boolean) -> Unit,
    onLoginClick: () -> Unit,
    navigateToSettings: () -> Unit
) {

    Scaffold(
        topBar = {
            TopBarComponent(
                navigateToSettings = navigateToSettings,
                navigateToSensores = {}
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 1. Banner Verde
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF9CCC65))
                    .padding(vertical = 24.dp, horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = "Android Logo",
                    tint = Color.White,
                    modifier = Modifier.size(64.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "\"The most\nsecure password\nmanager\"",
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Bob and Alice",
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // 2. Texto de instrução
            Text(text = "Digite suas credenciais para continuar")

            Spacer(modifier = Modifier.height(16.dp))

            // 3. Campo de Login
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 32.dp)
            ) {
                Text(text = "Login:", modifier = Modifier.width(60.dp))
                OutlinedTextField(
                    value = state.loginText,
                    onValueChange = onLoginChange,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // 4. Campo de Senha
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 32.dp)
            ) {
                Text(text = "Senha:", modifier = Modifier.width(60.dp))
                OutlinedTextField(
                    value = state.passwordText,
                    onValueChange = onPasswordChange,
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 5. Checkbox e Botão
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = state.saveLoginInfo,
                    onCheckedChange = onSaveLoginChange
                )
                Text(text = "Salvar as informações de login")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onLoginClick
            ) {
                Text(text = "Enviar")
            }
        }
    }
}
@Preview
@Composable
fun LoginScreenPreview() {
    MaterialTheme {
        Login_screen_content(
            state = LoginViewState(),
            onLoginChange = {},
            onPasswordChange = {},
            onSaveLoginChange = {},
            onLoginClick = {},
            navigateToSettings = {}
        )
    }
}

@Composable
fun MyAlertDialog(shouldShowDialog: MutableState<Boolean>) {
    if (shouldShowDialog.value) {
        AlertDialog(
            onDismissRequest = {
                shouldShowDialog.value = false
            },

            title = { Text(text = "Sobre") },
            text = { Text(text = "PlainText Password Manager v1.0") },
            confirmButton = {
                Button(
                    onClick = { shouldShowDialog.value = false }
                ) {
                    Text(text = "Ok")
                }
            }
        )
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TopBarComponent(
    navigateToSettings: (() -> Unit?)? = null,
    navigateToSensores: (() -> Unit?)? = null,
) {
    var expanded by remember { mutableStateOf(false) }
    val shouldShowDialog = remember { mutableStateOf(false) }

    if (shouldShowDialog.value) {
        MyAlertDialog(shouldShowDialog = shouldShowDialog)
    }

    TopAppBar(
        title = { Text("PlainText") },
        actions = {
            if (navigateToSettings != null && navigateToSensores != null) {
                IconButton(onClick = { expanded = true }) {
                    Icon(Icons.Default.MoreVert, contentDescription = "Menu")
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Configurações") },
                        onClick = {
                            navigateToSettings();
                            expanded = false;
                        },
                        modifier = Modifier.padding(8.dp)
                    )
                    DropdownMenuItem(
                        text = {
                            Text("Sobre");
                        },
                        onClick = {
                            shouldShowDialog.value = true;
                            expanded = false;
                        },
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    )
}
