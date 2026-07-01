package org.ukrida.hmifukridamobile.ui.admin

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Today
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import org.ukrida.hmifukridamobile.R
import org.ukrida.hmifukridamobile.UiState
import org.ukrida.hmifukridamobile.di.Injection
import org.ukrida.hmifukridamobile.navigation.Screen
import org.ukrida.hmifukridamobile.ui.Components.ParticipantApproveCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminEventDetailScreen(
    navController: NavController,
    eventId: Int
) {
    val context = LocalContext.current
    val viewModel: AdminEventDetailViewModel = viewModel(
        key = "admin_detail_$eventId",
        factory = AdminEventDetailViewModel.factory(
            eventId,
            Injection.provideEventRepository(),
            Injection.provideTokenManager(context)
        )
    )

    var expanded by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = Color(0xFFF4F6FA),
        topBar = {
            TopAppBar(
                title = { Text("HMIF-U Mobile") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, null)
                    }
                },
                actions = {
                    Box {
                        IconButton(onClick = { expanded = true }) {
                            Icon(Icons.Default.MoreVert, null)
                        }
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("Edit Event") },
                                onClick = {
                                    expanded = false
                                    navController.navigate(Screen.EditEvent.route)
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Close Registration") },
                                onClick = { expanded = false }
                            )
                            DropdownMenuItem(
                                text = { Text("Archive Event") },
                                onClick = { expanded = false }
                            )
                        }
                    }
                }
            )
        }
    ) { padding ->

        when (val eventState = viewModel.eventState) {
            is UiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize().padding(padding),
                    contentAlignment = Alignment.Center
                ) { CircularProgressIndicator() }
            }

            is UiState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize().padding(padding),
                    contentAlignment = Alignment.Center
                ) { Text(eventState.message, color = MaterialTheme.colorScheme.error) }
            }

            is UiState.Success -> {
                val event = eventState.data
                val registrantsState = viewModel.registrantsState

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(18.dp)
                ) {
                    item {
                        Text("Admin Dashboard / Event Detail", color = Color.Gray)
                        Spacer(modifier = Modifier.height(12.dp))

                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(20.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_launcher_foreground),
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

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(event.description, color = Color.Gray)

                        Spacer(modifier = Modifier.height(24.dp))
                    }

                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            val total = if (registrantsState is UiState.Success)
                                registrantsState.data.size else 0
                            EventStat(number = total.toString(), title = "Participants")
                        }
                    }

                    item {
                        Card(shape = RoundedCornerShape(20.dp)) {
                            Column(modifier = Modifier.padding(20.dp)) {
                                AdminInfoRow(Icons.Default.Today, event.eventDate)
                                Spacer(modifier = Modifier.height(12.dp))
                                AdminInfoRow(Icons.Default.LocationOn, event.location)
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

                    when (registrantsState) {
                        is UiState.Loading -> item {
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) { CircularProgressIndicator() }
                        }

                        is UiState.Error -> item {
                            Text(registrantsState.message, color = MaterialTheme.colorScheme.error)
                        }

                        is UiState.Success -> items(registrantsState.data) {
                            ParticipantApproveCard(registrant = it)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AdminInfoRow(icon: ImageVector, value: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(imageVector = icon, contentDescription = null, tint = Color(0xFF1565C0))
        Spacer(modifier = Modifier.width(12.dp))
        Text(value)
    }
}

@Composable
fun EventStat(number: String, title: String) {
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
            Text(text = title, color = Color.Gray)
        }
    }
}
