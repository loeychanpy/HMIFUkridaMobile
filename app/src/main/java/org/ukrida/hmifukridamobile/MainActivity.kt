package org.ukrida.hmifukridamobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import org.ukrida.hmifukridamobile.navigation.AppNavigation
import org.ukrida.hmifukridamobile.ui.theme.HMIFUkridaMobileTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HMIFUkridaMobileTheme {
                AppNavigation()
            }
        }
    }
}
