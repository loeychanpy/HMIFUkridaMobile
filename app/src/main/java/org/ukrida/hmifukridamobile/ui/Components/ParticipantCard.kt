package org.ukrida.hmifukridamobile.ui.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.ukrida.hmifukridamobile.data.model.Participant
import org.ukrida.hmifukridamobile.navigation.Screen

@Composable
fun ParticipantCard(

    participant: Participant,

    navController: NavController

) {

    val statusColor = when (participant.status) {

        "Registered" -> Color(0xFF2E7D32)

        "Waiting" -> Color(0xFFF9A825)

        else -> Color.Gray

    }

    Card(

        modifier = Modifier.fillMaxWidth(),

        shape = RoundedCornerShape(18.dp),

        elevation = CardDefaults.cardElevation(8.dp)

    ) {

        Column(

            modifier = Modifier.padding(18.dp)

        ) {

            Row(

                verticalAlignment = Alignment.CenterVertically

            ) {

                Box(

                    modifier = Modifier
                        .background(
                            Color(0xFFE3F2FD),
                            CircleShape
                        )
                        .padding(14.dp)

                ) {

                    Icon(

                        Icons.Default.Person,

                        contentDescription = null,

                        tint = Color(0xFF1565C0)

                    )

                }

                Column(

                    modifier = Modifier.padding(start = 16.dp)

                ) {

                    Text(

                        text = participant.name,

                        style = MaterialTheme.typography.titleMedium,

                        fontWeight = FontWeight.Bold

                    )

                    Text(

                        text = participant.nim,

                        color = Color.Gray

                    )

                }

            }

            Spacer(

                modifier = Modifier.height(18.dp)

            )

            Text(

                text = participant.major,

                style = MaterialTheme.typography.bodyMedium

            )

            Spacer(

                modifier = Modifier.height(6.dp)

            )

            Text(

                text = "Semester ${participant.semester}",

                color = Color.Gray

            )

            Spacer(

                modifier = Modifier.height(16.dp)

            )

            Text(

                text = participant.status,

                color = statusColor,

                fontWeight = FontWeight.Bold

            )

            Spacer(

                modifier = Modifier.height(18.dp)

            )

            Button(

                modifier = Modifier.fillMaxWidth(),

                onClick = {

                    navController.navigate(
                        Screen.ParticipantDetail.route
                    )

                },

                colors = ButtonDefaults.buttonColors(

                    containerColor = Color(0xFF1565C0)

                )

            ) {

                Text("View Detail")

            }

        }

    }

}