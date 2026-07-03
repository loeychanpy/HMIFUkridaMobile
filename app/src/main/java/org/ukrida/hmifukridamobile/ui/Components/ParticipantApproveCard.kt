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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.ukrida.hmifukridamobile.data.model.EventRegistrant

@Composable
fun ParticipantApproveCard(
    registrant: EventRegistrant,
    onAttendanceToggle: (registrationId: Int, attended: Boolean) -> Unit = { _, _ -> }
) {
    val colors = MaterialTheme.colorScheme

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(5.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(shape = CircleShape, color = colors.primaryContainer) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        tint = colors.onPrimaryContainer,
                        modifier = Modifier.padding(12.dp)
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(registrant.name, fontWeight = FontWeight.Bold)
                    Text(registrant.nim, color = colors.onSurfaceVariant)
                    Text(
                        registrant.email,
                        color = colors.onSurfaceVariant,
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        "Daftar: ${registrant.registeredAt}",
                        color = colors.onSurfaceVariant,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    modifier = Modifier.weight(1f),
                    onClick = { onAttendanceToggle(registrant.registrationId, true) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (registrant.attended) colors.tertiary else colors.tertiaryContainer,
                        contentColor = if (registrant.attended) colors.onTertiary else colors.onTertiaryContainer
                    )
                ) {
                    Icon(Icons.Default.CheckCircle, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Hadir", style = MaterialTheme.typography.labelMedium)
                }

                Button(
                    modifier = Modifier.weight(1f),
                    onClick = { onAttendanceToggle(registrant.registrationId, false) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (!registrant.attended) colors.error else colors.errorContainer,
                        contentColor = if (!registrant.attended) colors.onError else colors.onErrorContainer
                    )
                ) {
                    Icon(Icons.Outlined.Cancel, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Tidak Hadir", style = MaterialTheme.typography.labelMedium)
                }
            }
        }
    }
}
