package tech.axeinstd.anixapp.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import tech.axeinstd.anilibria.AniLibria
import tech.axeinstd.anilibria.data.account.LAccount
import tech.axeinstd.anixapp.data.storage.UserStorage
import tech.axeinstd.anixapp.dialogs.LoginDialog

@Composable
fun LoginScreen(AniLibriaClient: AniLibria, onComplete: (String) -> Unit, onDismiss: (Boolean) -> Unit) {
    val login = rememberSaveable { mutableStateOf("") }
    val passwd = rememberSaveable { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    val openAlertDialog = remember { mutableStateOf(false) }
    val authText = rememberSaveable { mutableStateOf("") }
    val isLoading = remember { mutableStateOf(false) }
    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Вход в аккаунт AniLibria",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                Spacer(
                    modifier = Modifier.height(15.dp)
                )
                OutlinedTextField(
                    value = login.value,
                    label = { Text("Логин") },
                    onValueChange = { login.value = it}
                )
                OutlinedTextField(
                    value = passwd.value,
                    label = { Text("Пароль") },
                    onValueChange = { passwd.value = it}
                )
                Spacer(
                    modifier = Modifier.height(15.dp)
                )
                if (isLoading.value) {
                    LinearProgressIndicator()
                    Spacer(
                        modifier = Modifier.height(5.dp)
                    )
                }
                Row {
                    ElevatedButton(
                        onClick = {
                            onDismiss(false)
                        }
                    ) {
                        Text("Пропустить")
                    }
                    Spacer(
                        modifier = Modifier.width(20.dp)
                    )
                    ElevatedButton(
                        onClick = {
                            coroutineScope.launch {
                                isLoading.value = true
                                val res: LAccount =  AniLibriaClient.auth(login.value, passwd.value)
                                isLoading.value = false
                                if (res.error != null) {
                                    authText.value = res.error
                                    openAlertDialog.value = true
                                }
                                else {
                                    onComplete(res.token ?: "")
                                }
                            }
                        }
                    ) {
                        Text("Войти")
                    }
                }
            }
        }
        when {
            openAlertDialog.value -> {
                LoginDialog(
                    text = authText.value,
                    onDismissRequest = { openAlertDialog.value = false }
                )
            }
        }
    }
}