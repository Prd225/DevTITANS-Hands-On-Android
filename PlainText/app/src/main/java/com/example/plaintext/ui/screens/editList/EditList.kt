package com.example.plaintext.ui.screens.editList

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.widget.Toast
import com.example.plaintext.data.model.PasswordInfo
import com.example.plaintext.ui.screens.Screen
import com.example.plaintext.ui.screens.login.TopBarComponent

data class EditListState(
    val nomeState: MutableState<String>,
    val usuarioState: MutableState<String>,
    val senhaState: MutableState<String>,
    val notasState: MutableState<String>,
)

fun isPasswordEmpty(password: PasswordInfo): Boolean {
    return password.name.isEmpty() && password.login.isEmpty() && password.password.isEmpty() && password.notes.isEmpty()
}

@Composable
fun EditList(
    args: Screen.EditList,
    navigateBack: () -> Unit,
    savePassword: (password: PasswordInfo, onResult: (Boolean) -> Unit) -> Unit
) {
    val isNew = args.password.id == 0
    val context = LocalContext.current
    var loginError by remember { mutableStateOf<String?>(null)}

    val editState = EditListState(
        nomeState = remember { mutableStateOf(args.password.name)},
        usuarioState = remember { mutableStateOf(args.password.login)},
        senhaState = remember { mutableStateOf(args.password.password)},
        notasState = remember {mutableStateOf(args.password.notes)},
    )

    Scaffold(
        topBar = { TopBarComponent() }
    ) {padding ->
        Column(
            modifier = Modifier.padding(padding)
                .fillMaxSize()

        ){
            Text(
                text = if (isNew) "Adicionar nova senha" else "Editar senha",
                fontSize = 20.sp,
                color = Color.Black,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFAED581))
                    .padding(16.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            EditInput(textInputLabel = "Nome", textInputState = editState.nomeState)
            EditInput(textInputLabel = "Usuário", textInputState = editState.usuarioState)

            if (loginError != null) {
                Text(
                    text = loginError!!,
                    color = Color.Red,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(horizontal = 30.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
            }

            EditInput(textInputLabel = "Senha", textInputState = editState.senhaState, isPassword = true)
            EditInput(
                textInputLabel = "Notas",
                textInputState = editState.notasState,
                textInputHeight = 150
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                val currentPassword = args.password.copy(
                    name = editState.nomeState.value,
                    login = editState.usuarioState.value,
                    password = editState.senhaState.value,
                    notes = editState.notasState.value
                )

                Button(
                    enabled = !isPasswordEmpty(currentPassword),
                    onClick = {
                        savePassword(currentPassword) { sucess ->
                            if (sucess){
                                Toast.makeText(context, "Senha salva com sucesso!", Toast.LENGTH_SHORT).show()
                                navigateBack()
                            } else {
                                loginError = "Já existe uma senha cadastrada com esse usuário, Escolha outro."
                            }
                        }
                    }
                ) {
                    Text("Salvar")
                }
            }
        }
    }
}

@Composable
fun EditInput(
    textInputLabel: String,
    textInputState: MutableState<String> = mutableStateOf(""),
    textInputHeight: Int = 60,
    isPassword: Boolean = false
) {
    val padding: Int = 30

    var textState by rememberSaveable { textInputState }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(textInputHeight.dp)
            .padding(horizontal = padding.dp),
        horizontalArrangement = Arrangement.Center,
    ) {
        OutlinedTextField(
            value = textState,
            onValueChange = { textState = it },
            label = { Text(textInputLabel) },
            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
            modifier = Modifier
                .height(textInputHeight.dp)
                .fillMaxWidth()
        )

    }
    Spacer(modifier = Modifier.height(10.dp))
}

@Preview(showBackground = true)
@Composable
fun EditListPreview() {
    EditList(
        Screen.EditList(PasswordInfo(0, "", "", "", "")),
        navigateBack = {},
        savePassword = { _, _ -> }
    )
}

@Preview(showBackground = true)
@Composable
fun EditInputPreview() {
    EditInput("Nome")
}