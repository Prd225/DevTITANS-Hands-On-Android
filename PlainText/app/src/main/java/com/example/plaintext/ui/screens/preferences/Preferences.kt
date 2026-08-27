package com.example.plaintext.ui.screens.preferences


import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.plaintext.data.model.PasswordInfo
import com.example.plaintext.ui.screens.Screen
import com.example.plaintext.ui.screens.editList.EditListState
import com.example.plaintext.ui.screens.login.Login_screen_content
import com.example.plaintext.ui.screens.login.TopBarComponent
import com.example.plaintext.ui.screens.util.PreferenceInput
import com.example.plaintext.ui.screens.util.PreferenceItem
import com.example.plaintext.ui.viewmodel.LoginViewState
import com.example.plaintext.ui.viewmodel.PreferencesViewModel
import com.example.plaintext.ui.viewmodel.PreferencesViewState
import kotlinx.coroutines.delay

data class PreferencesState(
    val login: String,
    val password: String,
    val preencher: Boolean,
)

@Composable
fun SettingsScreen(
    viewModel: PreferencesViewModel = hiltViewModel()
){
    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopBarComponent()
        }
    ){ padding ->
        SettingsContent(modifier = Modifier.padding(padding),
            state = viewModel.preferencesState,
            onLoginChange = { viewModel.updateLogin(it);
                Toast.makeText(context, "Preferências atualizadas", Toast.LENGTH_SHORT).show()
                            },
            onPasswordChange = { viewModel.updatePassword(it);
                Toast.makeText(context, "Preferências atualizadas", Toast.LENGTH_SHORT).show()
                               },
            onPreencherLoginChange = { viewModel.updatePreencher(it);
                Toast.makeText(context, "Preferências atualizadas", Toast.LENGTH_SHORT).show()
                                     },
        )
    }
}

@Composable
fun SettingsContent(
    modifier: Modifier = Modifier,
    state: PreferencesViewState,
    onLoginChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onPreencherLoginChange: (Boolean) -> Unit
    )
{
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())){

        PreferenceInput(
            title = "Preencher Login",
            label = "Login",
            fieldValue = state.login,
            summary = "Preencher login na tela inicial",
            onFinish = {fieldValue -> onLoginChange(fieldValue)}
        )

        PreferenceInput(
            title = "Setar Senha",
            label = "Label",
            fieldValue = state.password,
            summary = "Senha para entrar no sistema",
            onFinish = {fieldValue -> onPasswordChange(fieldValue)}
        )

        PreferenceItem(
            title = "Preencher Login",
            summary = "Preencher login na tela inicial",
            onClick = {
                // deve alterar o estado que representa se o switch está ligado ou não
                onPreencherLoginChange(!state.preencher)
            },
            control = {
                Switch(
                    checked = state.preencher, // deve ler o estado que representa se o switch está ligado ou não
                    onCheckedChange = {
                        // deve alterar o estado que representa se o switch está ligado ou não
                        onPreencherLoginChange(!state.preencher)
                    }
                )
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    MaterialTheme {
        SettingsContent(
            modifier = Modifier,
            state = PreferencesViewState(),
            onLoginChange = {},
            onPasswordChange = {},
            onPreencherLoginChange = {}
        )
    }
}