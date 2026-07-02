package org.ukrida.hmifukridamobile.ui.admin

import org.ukrida.hmifukridamobile.ui.viewmodel.AdminEventDetailViewModel
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FileDownload
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.QrCodeScanner
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import kotlinx.coroutines.launch
import org.ukrida.hmifukridamobile.R
import org.ukrida.hmifukridamobile.UiState
import org.ukrida.hmifukridamobile.di.Injection
import org.ukrida.hmifukridamobile.navigation.Screen
import org.ukrida.hmifukridamobile.ui.components.ParticipantApproveCard
import org.ukrida.hmifukridamobile.util.ExportUtils

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
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val scanLauncher = rememberLauncherForActivityResult(ScanContract()) { result ->
        val raw = result.contents ?: return@rememberLauncherForActivityResult
        val registrationId = raw.removePrefix("HMIF:REG:").toIntOrNull()
        if (registrationId != null) {
            viewModel.checkInByQr(registrationId)
        } else {
            coroutineScope.launch {
                snackbarHostState.showSnackbar("QR tidak valid.")
            }
        }
    }

    LaunchedEffect(viewModel.checkInMessage) {
        viewModel.checkInMessage?.let { msg ->
            snackbarHostState.showSnackbar(msg)
            viewModel.clearCheckInMessage()
        }
    }

    Scaffold(
        containerColor = Color(0xFFF4F6FA),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    val opts = ScanOptions().apply {
                        setPrompt("Arahkan kamera ke QR tiket peserta")
                        setBeepEnabled(true)
                        setOrientationLocked(false)
                    }
                    scanLauncher.launch(opts)
                },
                containerColor = Color(0xFF1565C0)
            ) {
                Icon(Icons.Default.QrCodeScanner, contentDescription = "Scan QR", tint = Color.White)
            }
        },
        topBar = {
            TopAppBar(
                title = { Text("HMIF-U Mobile") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, null)
                    }
                },
                actions = {
                    val registrantsForExport =
                        (viewModel.registrantsState as? UiState.Success)?.data ?: emptyList()
                    val eventTitleForExport =
                        (viewModel.eventState as? UiState.Success)?.data?.title ?: "event"

                    IconButton(onClick = {
                        coroutineScope.launch {
                            ExportUtils.exportRegistrantsToXlsx(
                                context,
                                registrantsForExport,
                                eventTitleForExport
                            )
                        }
                    }) {
                        Icon(Icons.Default.FileDownload, contentDescription = "Export to Excel")
                    }

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
                                    navController.navigate(Screen.EditEvent.createRoute(eventId))
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
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(110.dp), // Using a fixed height to guarantee identical box sizes
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            val registrantsList = (registrantsState as? UiState.Success)?.data ?: emptyList()
                            EventStat(
                                number = registrantsList.size.toString(),
                                title = "Peserta",
                                modifier = Modifier.weight(1f)
                            )
                            EventStat(
                                number = registrantsList.count { it.attended }.toString(),
                                title = "Hadir",
                                modifier = Modifier.weight(1f)
                            )
                            EventStat(
                                number = registrantsList.count { !it.attended }.toString(),
                                title = "Belum Hadir",
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }

                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(20.dp)
                        ) {
                            Column(modifier = Modifier.padding(20.dp)) {
                                AdminInfoRow(Icons.Default.Today, event.eventDate)
                                Spacer(modifier = Modifier.height(12.dp))
                                AdminInfoRow(Icons.Default.LocationOn, event.location)
                            }
                        }
                    }

                    item {
                        Text(
                            "Daftar Peserta & Kehadiran",
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

                        is UiState.Success -> items(registrantsState.data) { registrant ->
                            ParticipantApproveCard(
                                registrant = registrant,
                                onAttendanceToggle = { registrationId, attended ->
                                    viewModel.markAttendance(registrationId, attended)
                                }
                            )
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
fun EventStat(number: String, title: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxHeight(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = number,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1565C0),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = title,
                color = Color.Gray,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                lineHeight = MaterialTheme.typography.bodyMedium.lineHeight
            )
        }
    }
}
