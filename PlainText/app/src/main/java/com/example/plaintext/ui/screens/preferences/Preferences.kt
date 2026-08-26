package com.example.plaintext.ui.screens.preferences


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
import com.example.plaintext.ui.screens.login.TopBarComponent
import com.example.plaintext.ui.screens.util.PreferenceInput
import com.example.plaintext.ui.screens.util.PreferenceItem
import com.example.plaintext.ui.viewmodel.PreferencesState
import com.example.plaintext.ui.viewmodel.PreferencesViewModel
import kotlinx.coroutines.delay

//
/*
data class Preferences(
    val login: MutableState<String>,
    val password: MutableState<String>,
    val preencher: MutableState<Boolean>
)
 */

@Composable
fun SettingsScreen(navController: NavHostController?,
                   viewModel: PreferencesViewModel = hiltViewModel()
){
    Scaffold(
        topBar = {
            TopBarComponent()
        }
    ){ padding ->
        SettingsContent(modifier = Modifier.padding(padding), viewModel)
    }
}

@Composable
fun SettingsContent(modifier: Modifier = Modifier, viewModel: PreferencesViewModel,
                    textInputState: MutableState<String> = mutableStateOf(""),)
{
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())){

        PreferenceInput(
            title = "Preencher Login",
            label = "Login",
            fieldValue = viewModel.preferencesState.login,
            summary = "Preencher login na tela inicial",
            onFinish = {novoLogin -> viewModel.updateLogin(novoLogin)}
        )
        /*
        Column(
            modifier = Modifier

        ) {
            Text(
                text = viewModel.preferencesState.login,
                fontSize = 20.sp,
                color = Color.Black,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFAED581))
                    .padding(16.dp)
            )
        }
        */
        PreferenceInput(
            title = "Setar Senha",
            label = "Label",
            fieldValue = viewModel.preferencesState.password,
            summary = "Senha para entrar no sistema",
            onFinish = {novaSenha -> viewModel.updatePassword(novaSenha)}
        )

        PreferenceItem(
            title = "Preencher Login",
            summary = "Preencher login na tela inicial",
            onClick = {
                // deve alterar o estado que representa se o switch está ligado ou não
                //viewModel.updatePreencher(!viewModel.preferencesState.preencher)
            },
            control = {
                Switch(
                    checked = false, // deve ler o estado que representa se o switch está ligado ou não
                    onCheckedChange = {
                        // deve alterar o estado que representa se o switch está ligado ou não
                        //viewModel.updatePreencher(!viewModel.preferencesState.preencher)
                    }
                )
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    SettingsScreen(null)
}