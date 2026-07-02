package org.ukrida.hmifukridamobile.ui.home

import org.ukrida.hmifukridamobile.ui.viewmodel.HomeViewModel
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import org.ukrida.hmifukridamobile.UiState
import org.ukrida.hmifukridamobile.di.Injection
import org.ukrida.hmifukridamobile.navigation.Screen
import org.ukrida.hmifukridamobile.ui.components.BottomBar
import org.ukrida.hmifukridamobile.ui.components.SearchEventBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController
) {
    val context = LocalContext.current
    val viewModel: HomeViewModel = viewModel(
        factory = HomeViewModel.factory(
            Injection.provideEventRepository(),
            Injection.provideTokenManager(context)
        )
    )

    var search by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("All") }
    val categories = listOf("All", "Seminar", "Workshop", "Competition", "Talkshow", "Webinar")

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            "Hi, ${viewModel.userName.ifBlank { "..." }} 👋",
                            style = MaterialTheme.typography.titleLarge
                        )
                        Text(
                            "Find your next event",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            )
        },
        bottomBar = { BottomBar(navController) }
    ) { padding ->

        when (val state = viewModel.eventsState) {
            is UiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is UiState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    Text(state.message, color = MaterialTheme.colorScheme.error)
                }
            }

            is UiState.Success -> {
                val filtered = state.data.filter {
                    val matchesSearch = it.title.contains(search, ignoreCase = true) ||
                            it.location.contains(search, ignoreCase = true)
                    val matchesCategory = selectedCategory == "All" || it.category.equals(selectedCategory, ignoreCase = true)
                    matchesSearch && matchesCategory
                }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        SearchEventBar(
                            value = search,
                            onValueChange = { search = it }
                        )
                    }

                    item {
                        LazyRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(categories) { category ->
                                FilterChip(
                                    selected = selectedCategory == category,
                                    onClick = { selectedCategory = category },
                                    label = { Text(category) }
                                )
                            }
                        }
                    }

                    item {
                        Text("Upcoming Event", style = MaterialTheme.typography.titleLarge)
                    }

                    items(filtered) { event ->
                        EventCard(
                            event = event,
                            onClick = {
                                navController.navigate(Screen.Detail.createRoute(event.id))
                            }
                        )
                    }

                    if (filtered.size > 1) {
                        item {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("Popular Event", style = MaterialTheme.typography.titleLarge)
                        }

                        items(filtered.take(2)) { event ->
                            EventCard(
                                event = event,
                                onClick = {
                                    navController.navigate(Screen.Detail.createRoute(event.id))
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
