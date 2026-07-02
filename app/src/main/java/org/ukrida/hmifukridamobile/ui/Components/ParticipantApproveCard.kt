package org.ukrida.hmifukridamobile.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.ukrida.hmifukridamobile.data.model.EventRegistrant

@Composable
fun ParticipantApproveCard(
    registrant: EventRegistrant,
    onAttendanceToggle: (registrationId: Int, attended: Boolean) -> Unit = { _, _ -> }
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(5.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(shape = CircleShape, color = Color(0xFFE3F2FD)) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        tint = Color(0xFF1565C0),
                        modifier = Modifier.padding(12.dp)
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(registrant.name, fontWeight = FontWeight.Bold)
                    Text(registrant.nim, color = Color.Gray)
                    Text(
                        registrant.email,
                        color = Color.Gray,
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        "Daftar: ${registrant.registeredAt}",
                        color = Color.Gray,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Hadir button
                Button(
                    modifier = Modifier.weight(1f),
                    onClick = { onAttendanceToggle(registrant.registrationId, true) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (registrant.attended) Color(0xFF2E7D32) else Color(0xFFE8F5E9),
                        contentColor   = if (registrant.attended) Color.White else Color(0xFF2E7D32)
                    )
                ) {
                    Icon(
                        Icons.Default.CheckCircle,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Hadir", style = MaterialTheme.typography.labelMedium)
                }

                // Tidak Hadir button
                Button(
                    modifier = Modifier.weight(1f),
                    onClick = { onAttendanceToggle(registrant.registrationId, false) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (!registrant.attended) Color(0xFFC62828) else Color(0xFFFFEBEE),
                        contentColor   = if (!registrant.attended) Color.White else Color(0xFFC62828)
                    )
                ) {
                    Icon(
                        Icons.Outlined.Cancel,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Tidak Hadir", style = MaterialTheme.typography.labelMedium)
                }
            }
        }
    }
}
