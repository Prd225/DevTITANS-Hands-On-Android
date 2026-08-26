package com.example.plaintext.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

// 1. Data class que representa o estado da view
data class LoginViewState(
    val loginText: String = "",
    val passwordText: String = "",
    val saveLoginInfo: Boolean = false
)

// 2. O ViewModel no mesmo estilo do Hello.kt
@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

    // Variável que guarda o estado e só pode ser alterada aqui dentro
    var loginState by mutableStateOf(LoginViewState())
        private set

    // 3. Funções para alterar os estados das entradas de texto e botões
    fun updateLoginText(newLogin: String) {
        loginState = loginState.copy(loginText = newLogin)
    }

    fun updatePasswordText(newPassword: String) {
        loginState = loginState.copy(passwordText = newPassword)
    }

    fun updateSaveLoginInfo(newStatus: Boolean) {
        loginState = loginState.copy(saveLoginInfo = newStatus)
    }

    // Função para verificar as credenciais
    fun checkCredentials(): Boolean {
        // Exemplo de credenciais fixas (pode ser alterado conforme necessidade)
        return loginState.loginText == "devtitans" && loginState.passwordText == "123"
    }
}
