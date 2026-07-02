package org.ukrida.hmifukridamobile.ui.admin

import androidx.compose.foundation.Image
import org.ukrida.hmifukridamobile.R
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
import androidx.compose.foundation.clickable
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.ukrida.hmifukridamobile.data.model.Event
import org.ukrida.hmifukridamobile.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminEventCard(

    event: Event,

    navController: NavController

)
{

    var expanded by remember { mutableStateOf(false) }

    val isOpen = remember(event.eventDate) {
        runCatching {
            val eventDateTime = LocalDateTime.parse(
                event.eventDate.take(16),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
            )
            eventDateTime.isAfter(LocalDateTime.now())
        }.getOrDefault(false)
    }

    Card(

        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate(
                    Screen.AdminEventDetail.createRoute(event.id)
                )
            },
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {

        Column {
            Image(

                painter = painterResource(R.drawable.ic_launcher_foreground),

                contentDescription = null,

                modifier = Modifier
                    .fillMaxWidth()
                    .height(170.dp)
                    .clip(
                        RoundedCornerShape(
                            topStart = 20.dp,
                            topEnd = 20.dp
                        )
                    ),

                contentScale = ContentScale.Crop

            )

            Column(

                modifier = Modifier.padding(16.dp)

            ) {
                Row(

                    modifier = Modifier.fillMaxWidth(),

                    verticalAlignment = Alignment.CenterVertically

                ) {

                    Text(

                        text = event.title,

                        style = MaterialTheme.typography.titleLarge,

                        fontWeight = FontWeight.Bold,

                        modifier = Modifier.weight(1f)

                    )

                    Box {

                        IconButton(

                            onClick = {

                                expanded = true

                            }

                        ) {

                            Icon(

                                Icons.Default.MoreVert,

                                contentDescription = null

                            )

                        }

                        DropdownMenu(

                            expanded = expanded,

                            onDismissRequest = {

                                expanded = false

                            }

                        ) {
                            DropdownMenuItem(

                                text = {

                                    Text("Edit Event")

                                },

                                onClick = {

                                    expanded = false

                                    navController.navigate(
                                        Screen.EditEvent.createRoute(event.id)
                                    )

                                }

                            )

                            DropdownMenuItem(

                                text = {

                                    Text("History")

                                },

                                onClick = {

                                    expanded = false

                                    navController.navigate(
                                        Screen.HistoryDetail.createRoute(event.id)
                                    )

                                }

                            )

                        }

                    }

                }

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

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

                        text = event.eventDate,

                        modifier = Modifier.padding(start = 8.dp),

                        color = Color.Gray,

                        style = MaterialTheme.typography.bodyMedium

                    )

                }

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

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

                        color = Color.Gray,

                        style = MaterialTheme.typography.bodyMedium

                    )

                }

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                Text(

                    text = "👥 ${event.participantCount} Participants",

                    style = MaterialTheme.typography.bodyMedium,

                    color = Color(0xFF1565C0),

                    fontWeight = FontWeight.SemiBold

                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                Box(

                    modifier = Modifier
                        .background(
                            if (isOpen) Color(0xFFE8F5E9) else Color(0xFFEEEEEE),
                            RoundedCornerShape(50.dp)
                        )
                        .padding(
                            horizontal = 12.dp,
                            vertical = 6.dp
                        )

                ) {

                    Text(

                        text = if (isOpen) "Open Registration" else "Closed",

                        color = if (isOpen) Color(0xFF2E7D32) else Color.Gray,

                        style = MaterialTheme.typography.labelMedium,

                        fontWeight = FontWeight.Bold

                    )

                }

                Spacer(
                    modifier = Modifier.height(6.dp)
                )

            }

        }

    }

}
