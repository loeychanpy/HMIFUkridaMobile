package org.ukrida.hmifukridamobile.ui.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.ukrida.hmifukridamobile.data.model.Participant
import androidx.compose.foundation.BorderStroke

@Composable
fun ParticipantApproveCard(

    participant: Participant

) {

    var status by remember {

        mutableStateOf(participant.status)

    }

    Card(

        modifier = Modifier.fillMaxWidth(),

        shape = RoundedCornerShape(16.dp),

        elevation = CardDefaults.cardElevation(5.dp)

    ) {

        Column(

            modifier = Modifier.padding(16.dp)

        ) {

            Row(

                verticalAlignment = Alignment.CenterVertically

            ) {

                Surface(

                    shape = CircleShape,

                    color = Color(0xFFE3F2FD)

                ) {

                    Icon(

                        imageVector = Icons.Default.Person,

                        contentDescription = null,

                        tint = Color(0xFF1565C0),

                        modifier = Modifier.padding(12.dp)

                    )

                }

                Spacer(

                    modifier = Modifier.width(16.dp)

                )

                Column(

                    modifier = Modifier.weight(1f)

                ) {

                    Text(

                        participant.name,

                        fontWeight = FontWeight.Bold

                    )

                    Text(

                        participant.nim,

                        color = Color.Gray

                    )

                    Spacer(
                        modifier = Modifier.height(4.dp)
                    )

                    Text(

                        "${participant.major} • ${participant.semester}",

                        color = Color.Gray,

                        style = MaterialTheme.typography.bodySmall

                    )

                    Spacer(
                        modifier = Modifier.height(4.dp)
                    )

                    Text(

                        "Registered : ${participant.registerDate}",

                        color = Color.Gray,

                        style = MaterialTheme.typography.bodySmall

                    )

                }

                StatusChip(status)

            }

            Spacer(

                modifier = Modifier.height(16.dp)

            )

            if (status == "Waiting") {

                Row(

                    horizontalArrangement = Arrangement.spacedBy(12.dp)

                ) {

                    Button(

                        modifier = Modifier.weight(1f),

                        colors = ButtonDefaults.buttonColors(

                            containerColor = Color(0xFF1565C0)

                        ),

                        onClick = {

                            status = "Approved"

                        }

                    ) {

                        Text("Approve")

                    }

                    OutlinedButton(

                        modifier = Modifier.weight(1f),
                        border = BorderStroke(

                            1.dp,

                            Color.Red

                        ),
                        onClick = {

                            status = "Rejected"

                        }

                    ) {

                        Text("Reject")

                    }



                }

            }

        }

    }

}