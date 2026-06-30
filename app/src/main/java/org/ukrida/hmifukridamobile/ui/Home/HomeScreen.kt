package org.ukrida.hmifukridamobile.ui.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import org.ukrida.hmifukridamobile.ui.components.SearchEventBar
import org.ukrida.hmifukridamobile.data.dummy.DummyData
import org.ukrida.hmifukridamobile.navigation.Screen
import org.ukrida.hmifukridamobile.ui.components.BottomBar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController
) {

    val events = DummyData.eventList

    Scaffold(

        topBar = {

            TopAppBar(

                title = {

                    Column {

                        Text(

                            "Hi, Claudio 👋",

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

        bottomBar = {

            BottomBar(navController)

        }

    ) { padding ->

        var search by remember {

            mutableStateOf("")

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

                    onValueChange = {

                        search = it

                    }

                )

            }

            item {

                Text(

                    "Upcoming Event",

                    style = MaterialTheme.typography.titleLarge

                )

            }

            items(events) { event ->

                EventCard(

                    event = event,

                    onClick = {

                        navController.navigate(

                            Screen.Detail.route

                        )

                    }

                )

            }

            item {

                Spacer(

                    modifier = Modifier.height(8.dp)

                )

                Text(

                    "Popular Event",

                    style = MaterialTheme.typography.titleLarge

                )

            }

            items(events.take(2)) { event ->

                EventCard(

                    event = event,

                    onClick = {

                        navController.navigate(

                            Screen.Detail.route

                        )

                    }

                )

            }

        }

    }

}