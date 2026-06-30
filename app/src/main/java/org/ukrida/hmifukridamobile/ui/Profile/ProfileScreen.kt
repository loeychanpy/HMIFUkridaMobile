package org.ukrida.hmifukridamobile.ui.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.ukrida.hmifukridamobile.navigation.Screen
import org.ukrida.hmifukridamobile.ui.components.BottomBar
import androidx.compose.material3.Divider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController
) {

    Scaffold(

        topBar = {

            TopAppBar(

                title = {

                    Text("My Profile")

                }

            )

        },

        bottomBar = {

            BottomBar(navController)

        }

    ) { padding ->

        Column(

            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(20.dp),

            horizontalAlignment = Alignment.CenterHorizontally

        ) {

            Card(

                modifier = Modifier.fillMaxWidth(),

                elevation = CardDefaults.cardElevation(6.dp)

            ) {

                Column(

                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),

                    horizontalAlignment = Alignment.CenterHorizontally

                ) {

                    Icon(

                        imageVector = Icons.Default.AccountCircle,

                        contentDescription = null,

                        modifier = Modifier.size(90.dp)

                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(

                        "Richard Devin Sutisna",

                        style = MaterialTheme.typography.headlineSmall

                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text("412024019")

                    Text("Informatika")

                    Text("Semester 4")

                }

            }

            Spacer(modifier = Modifier.height(20.dp))

            Card(

                modifier = Modifier.fillMaxWidth(),

                elevation = CardDefaults.cardElevation(5.dp)

            ) {

                Column(

                    modifier = Modifier.padding(20.dp)

                ) {

                    ProfileItem(

                        "Email",

                        "Richard@gmail.com"

                    )

                    Divider()

                    ProfileItem(

                        "Program Studi",

                        "Informatika"

                    )

                    Divider()

                    ProfileItem(

                        "Semester",

                        "4"

                    )

                    Divider()

                    ProfileItem(

                        "Registered Event",

                        "3 Event"

                    )

                }

            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error
                ),
                onClick = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0)
                    }
                }
            ) {
                Text("Logout")
            }

        }

    }

}

@Composable
fun ProfileItem(

    title:String,

    value:String

){

    Column(

        modifier = Modifier.padding(vertical = 12.dp)

    ){

        Text(

            title,

            style = MaterialTheme.typography.labelMedium

        )

        Spacer(

            modifier = Modifier.height(2.dp)

        )

        Text(

            value,

            style = MaterialTheme.typography.bodyLarge

        )

    }

}