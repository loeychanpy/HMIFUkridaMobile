package org.ukrida.hmifukridamobile.ui.home

import androidx.compose.foundation.Image
import org.ukrida.hmifukridamobile.R
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.ukrida.hmifukridamobile.data.model.Event

@Composable
fun EventCard(

    event: Event,

    onClick: () -> Unit

) {

    Card(

        modifier = Modifier
            .fillMaxWidth()
            .clickable {

                onClick()

            },

        shape = RoundedCornerShape(20.dp),

        elevation = CardDefaults.cardElevation(8.dp)

    ) {

        Column {

            Image(

                painter = painterResource(id = R.drawable.ic_launcher_foreground),

                contentDescription = null,

                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),

                contentScale = ContentScale.Crop

            )

            Column(

                modifier = Modifier.padding(18.dp)

            ) {

                Text(

                    text = event.title,

                    style = MaterialTheme.typography.titleLarge,

                    fontWeight = FontWeight.Bold

                )

                Spacer(

                    modifier = Modifier.height(12.dp)

                )

                Row {

                    Icon(

                        Icons.Default.CalendarMonth,

                        null

                    )

                    Spacer(

                        modifier = Modifier.width(8.dp)

                    )

                    Text(

                        event.eventDate

                    )

                }

                Spacer(

                    modifier = Modifier.height(8.dp)

                )

                Row {

                    Icon(

                        Icons.Default.LocationOn,

                        null

                    )

                    Spacer(

                        modifier = Modifier.width(8.dp)

                    )

                    Text(

                        event.location

                    )

                }

                Spacer(

                    modifier = Modifier.height(16.dp)

                )

                Text(

                    event.description,

                    maxLines = 2

                )

                Spacer(

                    modifier = Modifier.height(20.dp)

                )

                Button(

                    modifier = Modifier.fillMaxWidth(),

                    onClick = {

                        onClick()

                    },

                    colors = ButtonDefaults.buttonColors(

                        containerColor = MaterialTheme.colorScheme.primary

                    )

                ) {

                    Text(

                        "View Detail"

                    )

                }

            }

        }

    }

}