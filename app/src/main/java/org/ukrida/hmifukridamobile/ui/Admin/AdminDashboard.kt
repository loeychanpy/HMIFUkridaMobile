package org.ukrida.hmifukridamobile.ui.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import org.ukrida.hmifukridamobile.data.dummy.DummyData
import org.ukrida.hmifukridamobile.navigation.Screen
import org.ukrida.hmifukridamobile.data.model.Event

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDashboard(
    navController: NavController
){

    val drawerState = rememberDrawerState(DrawerValue.Closed)

    val scope = rememberCoroutineScope()

    val events = DummyData.eventList

    ModalNavigationDrawer(

        drawerState = drawerState,

        drawerContent = {

            AdminDrawer(navController)

        }

    ) {

        Scaffold(

            topBar = {

                TopAppBar(

                    title = {

                        Text("HMIF Admin")

                    },

                    navigationIcon = {

                        IconButton(

                            onClick = {

                                scope.launch {

                                    drawerState.open()

                                }

                            }

                        ) {

                            Icon(
                                Icons.Default.Menu,
                                null
                            )

                        }

                    }

                )

            },

            floatingActionButton = {

                FloatingActionButton(

                    onClick = {

                        navController.navigate(
                            Screen.AddEvent.route
                        )

                    },

                    containerColor = Color(0xFF1565C0)

                ) {

                    Icon(
                        Icons.Default.Add,
                        null,
                        tint = Color.White
                    )

                }

            }

        ){ padding ->

            LazyColumn(

                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFF4F6FA))
                    .padding(padding),

                contentPadding = PaddingValues(20.dp),

                verticalArrangement = Arrangement.spacedBy(18.dp)

            ) {
                item{

                    Text(

                        text = "Welcome Back 👋",

                        style = MaterialTheme.typography.headlineSmall

                    )

                    Text(

                        text = "Administrator HMIF",

                        color = Color.Gray

                    )

                    Spacer(
                        modifier = Modifier.height(20.dp)
                    )

                }

                item{

                    Row(

                        modifier = Modifier.fillMaxWidth(),

                        horizontalArrangement =
                            Arrangement.spacedBy(12.dp)

                    ){

                        DashboardStatCard(

                            title = "Events",

                            value = "12",

                            modifier = Modifier.weight(1f)

                        )

                        DashboardStatCard(

                            title = "Students",

                            value = "245",

                            modifier = Modifier.weight(1f)

                        )

                    }

                    Spacer(
                        modifier = Modifier.height(12.dp)
                    )

                    Row(

                        modifier = Modifier.fillMaxWidth(),

                        horizontalArrangement =
                            Arrangement.spacedBy(12.dp)

                    ){

                        DashboardStatCard(

                            title = "Pending",

                            value = "18",

                            modifier = Modifier.weight(1f)

                        )

                        DashboardStatCard(

                            title = "History",

                            value = "5",

                            modifier = Modifier.weight(1f)

                        )

                    }

                }

                item{

                    Spacer(
                        modifier = Modifier.height(24.dp)
                    )

                    Text(

                        text = "Recent Events",

                        style = MaterialTheme.typography.titleLarge

                    )

                }

                items(DummyData.eventList) { event ->

                    AdminEventCard(

                        event = event,
                        navController = navController

                    )

                }

            }

        }

    }

}