package org.ukrida.hmifukridamobile.ui.registered

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.ukrida.hmifukridamobile.data.dummy.RegisteredEvent
import org.ukrida.hmifukridamobile.navigation.Screen

@Composable
fun RegisteredCard(

    event: RegisteredEvent,

    navController: NavController

) {

    val statusColor = when(event.status){

        "Confirmed" -> Color(0xFF2ECC71)

        "Registered" -> Color(0xFF3498DB)

        "Waiting" -> Color(0xFFF39C12)

        else -> Color.Red

    }

    Card(

        modifier = Modifier.fillMaxWidth(),

        shape = RoundedCornerShape(20.dp),

        elevation = CardDefaults.cardElevation(8.dp)

    ) {

        Column {

            Box {

                Image(

                    painter = painterResource(event.image),

                    contentDescription = null,

                    modifier = Modifier
                        .fillMaxWidth()
                        .height(190.dp)
                        .clip(
                            RoundedCornerShape(
                                topStart = 20.dp,
                                topEnd = 20.dp
                            )
                        ),

                    contentScale = ContentScale.Crop

                )

                Text(

                    text = event.status,

                    color = Color.White,

                    modifier = Modifier
                        .padding(12.dp)
                        .background(
                            statusColor,
                            RoundedCornerShape(20.dp)
                        )
                        .padding(
                            horizontal = 12.dp,
                            vertical = 5.dp
                        )

                )

            }

            Column(

                modifier = Modifier.padding(16.dp)

            ) {

                Text(

                    text = event.title,

                    style = MaterialTheme.typography.titleMedium,

                    fontWeight = FontWeight.Bold

                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(

                    verticalAlignment = Alignment.CenterVertically

                ) {

                    Icon(

                        Icons.Default.CalendarMonth,

                        contentDescription = null,

                        tint = Color.Gray,

                        modifier = Modifier.size(18.dp)

                    )

                    Text(

                        text = event.date,

                        modifier = Modifier.padding(start = 8.dp),

                        color = Color.Gray

                    )

                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(

                    verticalAlignment = Alignment.CenterVertically

                ) {

                    Icon(

                        Icons.Default.LocationOn,

                        contentDescription = null,

                        tint = Color.Gray,

                        modifier = Modifier.size(18.dp)

                    )

                    Text(

                        text = event.location,

                        modifier = Modifier.padding(start = 8.dp),

                        color = Color.Gray

                    )

                }

                Spacer(modifier = Modifier.height(18.dp))

                Button(

                    modifier = Modifier.fillMaxWidth(),

                    colors = ButtonDefaults.buttonColors(

                        containerColor = Color(0xFF1565C0)

                    ),

                    onClick = {

                        navController.navigate(Screen.Detail.route)

                    }

                ) {

                    Text("View Detail")

                }

            }

        }

    }

}