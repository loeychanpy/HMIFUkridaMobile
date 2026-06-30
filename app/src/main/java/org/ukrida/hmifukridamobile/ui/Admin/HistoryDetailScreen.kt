package org.ukrida.hmifukridamobile.ui.admin

import androidx.compose.foundation.Image
import org.ukrida.hmifukridamobile.R
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.People
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.ukrida.hmifukridamobile.data.dummy.DummyData


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryDetailScreen(

    navController: NavController

){

    val event = DummyData.eventList.first()

    Scaffold(

        topBar = {

            TopAppBar(

                title = {

                    Text("History Detail")

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

        Column(

            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(20.dp)

        ){

            Card(

                shape = RoundedCornerShape(20.dp)

            ){

                Image(

                    painter = painterResource(R.drawable.ic_launcher_foreground),

                    contentDescription = null,

                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),

                    contentScale = ContentScale.Crop

                )

            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(

                event.title,

                style = MaterialTheme.typography.headlineSmall,

                fontWeight = FontWeight.Bold

            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(

                event.description

            )

            Spacer(modifier = Modifier.height(24.dp))

            HistoryInfo(

                "Date",

                event.eventDate

            )

            HistoryInfo(

                "Location",

                event.location

            )

            HistoryInfo(

                "Participants",

                "120"

            )

            HistoryInfo(

                "Status",

                "Completed"

            )

        }

    }

}

@Composable
fun HistoryInfo(

    title:String,

    value:String

){

    Row(

        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),

        horizontalArrangement = Arrangement.SpaceBetween

    ){

        Text(

            title,

            fontWeight = FontWeight.Bold

        )

        Text(

            value

        )

    }

}

