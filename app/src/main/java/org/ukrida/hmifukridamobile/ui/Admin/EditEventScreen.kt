package org.ukrida.hmifukridamobile.ui.admin

import org.ukrida.hmifukridamobile.ui.viewmodel.EditEventViewModel
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import org.ukrida.hmifukridamobile.UiState
import org.ukrida.hmifukridamobile.di.Injection
import org.ukrida.hmifukridamobile.ui.components.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditEventScreen(
    navController: NavController,
    eventId: Int
) {
    val context = LocalContext.current
    val viewModel: EditEventViewModel = viewModel(
        key = "edit_event_$eventId",
        factory = EditEventViewModel.factory(
            eventId,
            Injection.provideEventRepository(),
            Injection.provideTokenManager(context)
        )
    )

    var title by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var quota by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    LaunchedEffect(viewModel.eventState) {
        if (viewModel.eventState is UiState.Success) {
            val e = (viewModel.eventState as UiState.Success).data
            title = e.title
            location = e.location
            description = e.description
            // eventDate format: "2026-08-01 09:00:00" — split into date and time fields
            date = e.eventDate.take(10)
            time = e.eventDate.drop(11).take(5)
        }
    }

    LaunchedEffect(viewModel.updateState) {
        when (val s = viewModel.updateState) {
            is UiState.Success -> {
                Toast.makeText(context, s.data, Toast.LENGTH_SHORT).show()
                navController.popBackStack()
            }
            is UiState.Error -> Toast.makeText(context, s.message, Toast.LENGTH_SHORT).show()
            else -> {}
        }
    }

    Scaffold(
        containerColor = Color(0xFFF4F6FA),
        topBar = {
            TopAppBar(
                title = { Text("HMIF-U Mobile") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, null)
                    }
                }
            )
        }
    ) { padding ->

        when (val state = viewModel.eventState) {
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
                ) { Text(state.message, color = MaterialTheme.colorScheme.error) }
            }

            is UiState.Success -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .verticalScroll(rememberScrollState())
                        .padding(20.dp)
                ) {
                    Text(text = "Admin Dashboard / Edit Event", color = Color.Gray)

                    Spacer(modifier = Modifier.height(20.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = "Edit Event", style = MaterialTheme.typography.headlineMedium)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = "Update information for this event.", color = Color.Gray)
                        }
                        AssistChip(
                            onClick = {},
                            label = { Text("Admin") },
                            leadingIcon = { Icon(Icons.Default.AdminPanelSettings, null) }
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = MaterialTheme.shapes.large
                    ) {
                        Column(modifier = Modifier.padding(20.dp)) {
                            UploadBannerCard()

                            Spacer(modifier = Modifier.height(20.dp))

                            EventTextField(
                                title = "Event Title",
                                value = title,
                                onValueChange = { title = it }
                            )

                            Spacer(modifier = Modifier.height(18.dp))

                            CategoryDropdown(value = category, onSelected = { category = it })

                            Spacer(modifier = Modifier.height(18.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                DatePickerField(
                                    modifier = Modifier.weight(1f),
                                    value = date,
                                    onValueChange = { date = it }
                                )
                                TimePickerField(
                                    modifier = Modifier.weight(1f),
                                    value = time,
                                    onValueChange = { time = it }
                                )
                            }

                            Spacer(modifier = Modifier.height(18.dp))

                            EventTextField(
                                title = "Location",
                                value = location,
                                onValueChange = { location = it }
                            )

                            Spacer(modifier = Modifier.height(18.dp))

                            EventTextField(
                                title = "Participant Quota",
                                value = quota,
                                onValueChange = { quota = it }
                            )

                            Spacer(modifier = Modifier.height(18.dp))

                            DescriptionField(
                                value = description,
                                onValueChange = { description = it }
                            )

                            Spacer(modifier = Modifier.height(28.dp))

                            val isUpdating = viewModel.updateState is UiState.Loading

                            Button(
                                modifier = Modifier.fillMaxWidth(),
                                onClick = {
                                    if (title.isBlank() || date.isBlank() || location.isBlank() || description.isBlank()) return@Button
                                    viewModel.updateEvent(title, date, time, description, location)
                                },
                                enabled = !isUpdating,
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1565C0))
                            ) {
                                if (isUpdating) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.size(18.dp),
                                        color = Color.White,
                                        strokeWidth = 2.dp
                                    )
                                } else {
                                    Text("Update Event")
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            OutlinedButton(
                                modifier = Modifier.fillMaxWidth(),
                                onClick = { navController.popBackStack() }
                            ) {
                                Text("Cancel")
                            }

                            Spacer(modifier = Modifier.height(20.dp))
                        }
                    }
                }
            }
        }
    }
}