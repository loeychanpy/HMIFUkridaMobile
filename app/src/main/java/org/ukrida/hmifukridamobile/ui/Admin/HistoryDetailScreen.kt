package org.ukrida.hmifukridamobile.ui.admin

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import org.ukrida.hmifukridamobile.UiState
import org.ukrida.hmifukridamobile.di.Injection
import org.ukrida.hmifukridamobile.ui.viewmodel.DetailEventViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryDetailScreen(
    navController: NavController,
    eventId: Int
) {
    val context = LocalContext.current
    val viewModel: DetailEventViewModel = viewModel(
        key = "history_detail_$eventId",
        factory = DetailEventViewModel.factory(
            eventId,
            Injection.provideEventRepository(),
            Injection.provideTokenManager(context)
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("History Detail") },
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
                val event = state.data
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .verticalScroll(rememberScrollState())
                        .padding(20.dp)
                ) {
                    Text(
                        text = event.title,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            HorizontalDivider()
                            HistoryInfo("Tanggal", event.eventDate)
                            HorizontalDivider()
                            HistoryInfo("Lokasi", event.location)
                            HorizontalDivider()
                            HistoryInfo("Peserta", "${event.participantCount} orang")
                            HorizontalDivider()
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = "Deskripsi",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(text = event.description, color = Color.Gray)
                }
            }
        }
    }
}

@Composable
fun HistoryInfo(title: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(title, style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
        Text(value, fontWeight = FontWeight.SemiBold)
    }
}