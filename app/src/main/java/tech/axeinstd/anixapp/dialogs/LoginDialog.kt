package tech.axeinstd.anixapp.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.acos

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginDialog(
    onDismissRequest: () -> Unit,
    text: String
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp

    var acceptedModifier = if (screenWidth < 600.dp) Modifier.fillMaxWidth() else Modifier.width(600.dp)
    acceptedModifier = if (screenHeight < 120.dp) acceptedModifier.fillMaxHeight() else acceptedModifier.height(120.dp)
    BasicAlertDialog(
        onDismissRequest = { onDismissRequest() },
    ) {
        Card(
            modifier = acceptedModifier
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(Icons.Rounded.Info, contentDescription = "Авторизация не удалась", modifier = Modifier.width(32.dp).height(32.dp))
                Spacer(Modifier.height(5.dp))
                Text("Ошибка авторизации", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(15.dp))
                Text(text)
            }
        }
    }
}