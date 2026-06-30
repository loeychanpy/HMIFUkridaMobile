package org.ukrida.hmifukridamobile.ui.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.ukrida.hmifukridamobile.navigation.Screen

@Composable
fun AdminDrawer(

    navController: NavController

) {

    Column(

        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(0.78f)
            .background(Color.White)

    ) {

        Column(

            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF1565C0))
                .padding(24.dp)

        ) {

            Text(

                text = "HMIF Admin",

                style = MaterialTheme.typography.headlineSmall,

                color = Color.White,

                fontWeight = FontWeight.Bold

            )

            Spacer(
                modifier = Modifier.height(6.dp)
            )

            Text(

                text = "Administrator Panel",

                color = Color.White.copy(alpha = 0.8f)

            )

        }

        Spacer(
            modifier = Modifier.height(18.dp)
        )

        DrawerItem(

            icon = Icons.Default.Home,

            title = "Dashboard"

        ){

            navController.navigate(
                Screen.AdminDashboard.route
            )

        }

        DrawerItem(

            icon = Icons.Default.Add,

            title = "Add Event"

        ){

            navController.navigate(
                Screen.AddEvent.route
            )

        }

        DrawerItem(

            icon = Icons.Default.Event,

            title = "Manage Events"

        ){

            navController.navigate(
                Screen.AdminDashboard.route
            )

        }

        DrawerItem(

            icon = Icons.Default.Groups,

            title = "Participants"

        ){

            navController.navigate(
                Screen.Participant.route
            )

        }

        DrawerItem(

            icon = Icons.Default.History,

            title = "History"

        ){

            navController.navigate(
                Screen.History.route
            )

        }

        Divider(
            modifier = Modifier.padding(vertical = 16.dp)
        )

        DrawerItem(

            icon = Icons.Default.Logout,

            title = "Logout"

        ){

            navController.navigate(
                Screen.Login.route
            )

        }

    }

}

@Composable
private fun DrawerItem(

    icon: androidx.compose.ui.graphics.vector.ImageVector,

    title: String,

    onClick: () -> Unit

) {

    Row(

        modifier = Modifier
            .fillMaxWidth()
            .clickable {

                onClick()

            }
            .padding(
                horizontal = 22.dp,
                vertical = 16.dp
            ),

        verticalAlignment = Alignment.CenterVertically,

        horizontalArrangement = Arrangement.Start

    ) {

        Icon(

            imageVector = icon,

            contentDescription = null,

            tint = Color(0xFF1565C0),

            modifier = Modifier.size(24.dp)

        )

        Text(

            text = title,

            modifier = Modifier.padding(start = 18.dp),

            style = MaterialTheme.typography.bodyLarge

        )

    }

}