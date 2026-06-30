package org.ukrida.hmifukridamobile.ui.admin

import androidx.navigation.NavController
import org.ukrida.hmifukridamobile.data.dummy.DummyData
import org.ukrida.hmifukridamobile.navigation.Screen
import org.ukrida.hmifukridamobile.ui.components.HistoryCard
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(

    navController: NavController

){

    Scaffold(

        topBar = {

            TopAppBar(

                title = {

                    Text("History")

                },

                navigationIcon = {

                    IconButton(

                        onClick = {

                            navController.popBackStack()

                        }

                    ){

                        Icon(

                            Icons.Default.ArrowBack,

                            null

                        )

                    }

                }

            )

        }

    ){ padding ->

        LazyColumn(

            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(20.dp),

            verticalArrangement = Arrangement.spacedBy(18.dp)

        ){

            items(

                DummyData.eventList

            ) { event ->

                HistoryCard(

                    event = event,

                    onClick = {

                        navController.navigate(
                            Screen.HistoryDetail.route
                        )

                    }

                )

            }

        }

    }

}