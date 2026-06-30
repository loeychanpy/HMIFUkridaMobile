package org.ukrida.hmifukridamobile.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import org.ukrida.hmifukridamobile.navigation.Screen

@Composable
fun BottomBar(
    navController: NavController
) {

    NavigationBar {

        NavigationBarItem(

            selected = false,

            onClick = {

                navController.navigate(Screen.Home.route)

            },

            icon = {

                Icon(
                    Icons.Default.Home,
                    contentDescription = "Home"
                )

            },

            label = {

                Text("Home")

            }

        )

        NavigationBarItem(

            selected = false,

            onClick = {

                navController.navigate(Screen.Registered.route)

            },

            icon = {

                Icon(

                    Icons.Default.Assignment,

                    contentDescription = "Registered"

                )

            },

            label = {

                Text("Registered")

            }

        )

        NavigationBarItem(

            selected = false,

            onClick = {

                navController.navigate(Screen.Profile.route)

            },

            icon = {

                Icon(
                    Icons.Default.Person,
                    contentDescription = "Profile"
                )

            },

            label = {

                Text("Profile")

            }

        )

    }

}