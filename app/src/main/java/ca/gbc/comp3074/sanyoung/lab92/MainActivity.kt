
package ca.gbc.comp3074.sanyoung.lab92

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ca.gbc.comp3074.sanyoung.lab92.components.ContactApp
import ca.gbc.comp3074.sanyoung.lab92.ui.theme.Lab92Theme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            Lab92Theme {
                ContactApp()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Lab92Theme {
        ContactApp()
    }
}
