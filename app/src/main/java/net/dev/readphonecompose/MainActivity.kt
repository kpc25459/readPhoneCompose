package net.dev.readphonecompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import net.dev.readphone.ui.theme.ReadPhoneComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ReadPhoneComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    Content()
                }
            }
        }
    }
}

@Preview
@Composable
private fun Content() {

    Column(verticalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxHeight()) {
        Row(verticalAlignment = Alignment.Top) {
            Column {
                Item("kot1")
                Item("kot2")
                Item("kot3")
            }

        }

        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.Center) {
            Button(onClick = {

                //Toast.makeText(LocalContext.current, "Test", Toast.LENGTH_SHORT).show()

            }) {

                Text(text = "Kliknij")
            }
        }

    }
}

@Composable
fun Item(name: String) {
    Text(text = "Hello $name!")
}