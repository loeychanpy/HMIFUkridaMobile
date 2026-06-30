package org.ukrida.hmifukridamobile.ui.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Today
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.ukrida.hmifukridamobile.data.dummy.DummyData
import org.ukrida.hmifukridamobile.navigation.Screen
import org.ukrida.hmifukridamobile.ui.Components.ParticipantApproveCard
import org.ukrida.hmifukridamobile.ui.Components.StatusChip
import androidx.compose.foundation.Image
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminEventDetailScreen(

    navController: NavController

) {

    var expanded by remember {

        mutableStateOf(false)

    }

    val event = DummyData.eventList.first()

    val participantList = DummyData.participantList

    Scaffold(

        containerColor = Color(0xFFF4F6FA),

        topBar = {

            TopAppBar(

                title = {

                    Text("HMIF-U Mobile")

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

                },

                actions = {

                    Box {

                        IconButton(

                            onClick = {

                                expanded = true

                            }

                        ) {

                            Icon(

                                Icons.Default.MoreVert,

                                null

                            )

                        }

                        DropdownMenu(

                            expanded = expanded,

                            onDismissRequest = {

                                expanded = false

                            }

                        ) {

                            DropdownMenuItem(

                                text = {

                                    Text("Edit Event")

                                },

                                onClick = {

                                    expanded = false

                                    navController.navigate(

                                        Screen.EditEvent.route

                                    )

                                }

                            )

                            DropdownMenuItem(

                                text = {

                                    Text("Close Registration")

                                },

                                onClick = {

                                    expanded = false

                                }

                            )

                            DropdownMenuItem(

                                text = {

                                    Text("Archive Event")

                                },

                                onClick = {

                                    expanded = false

                                }

                            )

                        }

                    }

                }

            )

        }

    ) { padding ->
        LazyColumn(

            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(20.dp),

            verticalArrangement = Arrangement.spacedBy(18.dp)

        ) {

            item {

                Text(

                    "Admin Dashboard / Event Detail",

                    color = Color.Gray

                )

                Spacer(

                    modifier = Modifier.height(12.dp)

                )

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp)
                ) {

                    Image(
                        painter = painterResource(id = event.image),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp),
                        contentScale = ContentScale.Crop
                    )

                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(

                    event.title,

                    style = MaterialTheme.typography.headlineMedium,

                    fontWeight = FontWeight.Bold

                )

                Spacer(

                    modifier = Modifier.height(8.dp)

                )

                Text(

                    event.description,

                    color = Color.Gray

                )

                Spacer(

                    modifier = Modifier.height(24.dp)

                )

            }

            item {

                Row(

                    modifier = Modifier.fillMaxWidth(),

                    horizontalArrangement = Arrangement.SpaceEvenly

                ) {

                    EventStat(
                        number = "120",
                        title = "Participants"
                    )

                    EventStat(
                        number = "85",
                        title = "Approved"
                    )

                    EventStat(
                        number = "35",
                        title = "Waiting"
                    )

                }

            }

            item {

                Card(

                    shape = RoundedCornerShape(20.dp)

                ) {

                    Column(

                        modifier = Modifier.padding(20.dp)

                    ) {

                        InfoRow(

                            Icons.Default.Today,

                            event.date

                        )

                        Spacer(

                            modifier = Modifier.height(12.dp)

                        )

                        InfoRow(

                            Icons.Default.Schedule,

                            "09.00 WIB"

                        )

                        Spacer(

                            modifier = Modifier.height(12.dp)

                        )

                        InfoRow(

                            Icons.Default.LocationOn,

                            event.location

                        )

                        Spacer(

                            modifier = Modifier.height(12.dp)

                        )

                        InfoRow(

                            Icons.Default.People,

                            "120 / 150 Participants"

                        )

                    }

                }

            }

            item {

                Text(

                    "Participants",

                    style = MaterialTheme.typography.headlineSmall,

                    fontWeight = FontWeight.Bold

                )

            }

            items(

                participantList

            ) {

                ParticipantApproveCard(

                    participant = it

                )

            }

        }

    }

}

@Composable
fun InfoRow(

    icon: ImageVector,

    value: String

){

    Row(

        verticalAlignment = Alignment.CenterVertically

    ){

        Icon(

            imageVector = icon,

            contentDescription = null,

            tint = Color(0xFF1565C0)

        )

        Spacer(

            modifier = Modifier.width(12.dp)

        )

        Text(

            value

        )

    }

}

@Composable
fun EventStat(

    number: String,

    title: String

) {

    Card(

        shape = RoundedCornerShape(16.dp),

        elevation = CardDefaults.cardElevation(4.dp)

    ) {

        Column(

            modifier = Modifier.padding(16.dp),

            horizontalAlignment = Alignment.CenterHorizontally

        ) {

            Text(

                text = number,

                style = MaterialTheme.typography.headlineSmall,

                fontWeight = FontWeight.Bold,

                color = Color(0xFF1565C0)

            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(

                text = title,

                color = Color.Gray

            )

        }

    }

}