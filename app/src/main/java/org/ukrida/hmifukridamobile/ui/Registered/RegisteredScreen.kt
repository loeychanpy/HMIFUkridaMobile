package org.ukrida.hmifukridamobile.ui.registered

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.ukrida.hmifukridamobile.data.dummy.DummyData
import org.ukrida.hmifukridamobile.ui.components.BottomBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisteredScreen(
    navController: NavController
) {

    val registeredList = DummyData.registeredList

    Scaffold(

        topBar = {

            TopAppBar(

                title = {

                    Text(
                        "My Registered Events"
                    )

                }

            )

        },

        bottomBar = {

            BottomBar(navController)

        }

    ) { padding ->

        Column(

            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF4F6FA))
                .padding(padding)

        ) {

            Text(

                text = "Semua event yang sudah kamu daftarkan.",

                style = MaterialTheme.typography.bodyMedium,

                modifier = Modifier.padding(
                    horizontal = 20.dp,
                    vertical = 12.dp
                )

            )

            LazyColumn(

                modifier = Modifier.fillMaxSize(),

                contentPadding = PaddingValues(
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 90.dp
                ),

                verticalArrangement = Arrangement.spacedBy(16.dp)

            ) {

                items(registeredList) { event ->

                    RegisteredCard(
                        event = event,
                        navController = navController
                    )

                }

            }

        }

    }

}