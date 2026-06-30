package org.ukrida.hmifukridamobile.ui.detail

import androidx.compose.foundation.Image
import org.ukrida.hmifukridamobile.R
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.ukrida.hmifukridamobile.data.dummy.DummyData
import androidx.compose.ui.graphics.vector.ImageVector

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailEventScreen(

    navController: NavController

) {

    val event = DummyData.eventList.first()

    Scaffold(

        topBar = {

            TopAppBar(

                title = {

                    Text("Event Detail")

                },

                navigationIcon = {

                    IconButton(

                        onClick = {

                            navController.popBackStack()

                        }

                    ) {

                        Icon(

                            Icons.Default.ArrowBack,

                            null

                        )

                    }

                }

            )

        }

    ) { padding ->

        Column(

            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)

        ) {

            Card(

                shape = RoundedCornerShape(20.dp)

            ) {

                Image(

                    painter = painterResource(R.drawable.ic_launcher_foreground),

                    contentDescription = null,

                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp),

                    contentScale = ContentScale.Crop

                )

            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(

                event.title,

                style = MaterialTheme.typography.headlineSmall,

                fontWeight = FontWeight.Bold

            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(

                event.description,

                color = MaterialTheme.colorScheme.onSurfaceVariant

            )

            Spacer(modifier = Modifier.height(20.dp))

            Card(

                shape = RoundedCornerShape(18.dp)

            ) {

                Column(

                    modifier = Modifier.padding(16.dp)

                ) {

                    DetailRow(

                        Icons.Default.CalendarMonth,

                        event.eventDate

                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    DetailRow(

                        Icons.Default.Schedule,

                        "09.00 WIB"

                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    DetailRow(

                        Icons.Default.LocationOn,

                        event.location

                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    DetailRow(

                        Icons.Default.People,

                        "120 / 150 Seats"

                    )

                }

            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(

                "Description",

                style = MaterialTheme.typography.titleLarge,

                fontWeight = FontWeight.Bold

            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(

                event.description

            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(

                modifier = Modifier.fillMaxWidth(),

                onClick = {

                    // Dummy register

                }

            ) {

                Text("Register Event")

            }

            Spacer(modifier = Modifier.height(20.dp))

        }

    }

}

@Composable
fun DetailRow(

    icon: ImageVector,

    text: String

) {

    Row(

        verticalAlignment = Alignment.CenterVertically

    ) {

        Icon(

            imageVector = icon,

            contentDescription = null

        )

        Spacer(

            modifier = Modifier.width(12.dp)

        )

        Text(

            text

        )

    }

}