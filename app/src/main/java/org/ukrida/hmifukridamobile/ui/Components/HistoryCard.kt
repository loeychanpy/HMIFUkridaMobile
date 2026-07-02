package org.ukrida.hmifukridamobile.ui.components

import androidx.compose.foundation.Image
import org.ukrida.hmifukridamobile.R
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.ukrida.hmifukridamobile.data.model.Event

@Composable
fun HistoryCard(

    event: Event,

    onClick:()->Unit

){

    Card(

        modifier = Modifier
            .fillMaxWidth()
            .clickable {

                onClick()

            },

        shape = RoundedCornerShape(18.dp)

    ){

        Column{

            Image(

                painter = painterResource(R.drawable.ic_launcher_foreground),

                contentDescription = null,

                modifier = Modifier
                    .fillMaxWidth()
                    .height(170.dp),

                contentScale = ContentScale.Crop

            )

            Column(

                modifier = Modifier.padding(16.dp)

            ){

                Text(

                    event.title,

                    style = MaterialTheme.typography.titleLarge,

                    fontWeight = FontWeight.Bold

                )

                Spacer(

                    modifier = Modifier.height(8.dp)

                )

                Text(

                    "Finished",

                    color = MaterialTheme.colorScheme.primary

                )

                Spacer(

                    modifier = Modifier.height(6.dp)

                )

                Text(

                    event.eventDate

                )

                Spacer(

                    modifier = Modifier.height(6.dp)

                )

                Text(

                    "Participants : ${event.participantCount}"

                )

            }

        }

    }

}