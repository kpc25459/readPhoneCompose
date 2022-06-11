package net.dev.readphonecompose

import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.widget.TextView
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
import androidx.compose.ui.platform.LocalContext
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


    @Preview
    @Composable
    private fun Content() {

        Column(verticalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxHeight()) {
            Row(verticalAlignment = Alignment.Top) {
                Column {
                }

            }

            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.Center) {
                Button(onClick = {
                    retrieveData(LocalContext.current)
                }) {
                    Text(text = "Kliknij")
                }
            }
        }
    }

    private fun retrieveData(context: Context) {

        val inbox = Utils.readInboxConversations(context)
        printList("odebrane: ", inbox)


        val sent = Utils.readSentConversations(context)
        printList("wysłane: ", sent)


        val draft = Utils.readDraftConversations(context)
        printList("robocze: ", draft)

        val contacts = Utils.readContacts(context)
        printList("kontakty: ", contacts)

        val callLog = Utils.readCalls(context)
        printList("połączenia: ", callLog)
    }

    private fun printList(title: String, inbox: List<String>) {

        val textView = TextView(context).apply {
            text = title
            textSize = 14.0f
        }
        textView.setTypeface(textView.typeface, Typeface.BOLD_ITALIC)

        binding.listLayout.addView(textView)

        for (msg in inbox) {
            binding.listLayout.addView(TextView(requireContext()).apply {
                text = msg
            })
        }
    }


}
