package org.ukrida.hmifukridamobile.ui.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.ukrida.hmifukridamobile.ui.components.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEventScreen(

    navController: NavController

) {

    var title by remember { mutableStateOf("") }

    var category by remember { mutableStateOf("") }

    var date by remember { mutableStateOf("") }

    var time by remember { mutableStateOf("") }

    var location by remember { mutableStateOf("") }

    var quota by remember { mutableStateOf("") }

    var description by remember { mutableStateOf("") }

    Scaffold(

        containerColor = Color(0xFFF4F6FA),

        topBar = {

            TopAppBar(

                title = {

                    Text("HMIF-U Mobile")

                },

                navigationIcon = {

                    IconButton(

                        onClick = {

                            navController.popBackStack()

                        }

                    ) {

                        Icon(
                            Icons.Default.ArrowBack,
                            null
                        )

                    }

                }

            )

        }

    ) { padding ->

        Column(

            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(20.dp)

        ) {

            Text(

                text = "Admin Dashboard / Add Event",

                color = Color.Gray

            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(

                modifier = Modifier.fillMaxWidth(),

                verticalAlignment = Alignment.CenterVertically

            ) {

                Column(

                    modifier = Modifier.weight(1f)

                ) {

                    Text(

                        text = "Create Event",

                        style = MaterialTheme.typography.headlineMedium

                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(

                        text = "Create and publish event for HMIF students.",

                        color = Color.Gray

                    )

                }

                AssistChip(

                    onClick = {},

                    label = {

                        Text("Admin")

                    },

                    leadingIcon = {

                        Icon(

                            Icons.Default.AdminPanelSettings,

                            null

                        )

                    }

                )

            }

            Spacer(modifier = Modifier.height(24.dp))

            Card(

                modifier = Modifier.fillMaxWidth(),

                shape = MaterialTheme.shapes.large

            ) {

                Column(

                    modifier = Modifier.padding(20.dp)

                ) {

                    UploadBannerCard()

                    Spacer(modifier = Modifier.height(20.dp))

                    EventTextField(

                        title = "Event Title",

                        value = title,

                        onValueChange = {

                            title = it

                        }

                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    CategoryDropdown(

                        value = category,

                        onSelected = {

                            category = it

                        }

                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    Row(

                        modifier = Modifier.fillMaxWidth(),

                        horizontalArrangement = Arrangement.spacedBy(12.dp)

                    ) {

                        DatePickerField(

                            modifier = Modifier.weight(1f),

                            value = date,

                            onValueChange = {

                                date = it

                            }

                        )

                        TimePickerField(

                            modifier = Modifier.weight(1f),

                            value = time,

                            onValueChange = {

                                time = it

                            }

                        )

                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    EventTextField(

                        title = "Location",

                        value = location,

                        onValueChange = {

                            location = it

                        }

                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    EventTextField(

                        title = "Participant Quota",

                        value = quota,

                        onValueChange = {

                            quota = it

                        }

                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    DescriptionField(

                        value = description,

                        onValueChange = {

                            description = it

                        }

                    )

                    Spacer(modifier = Modifier.height(28.dp))

                    PublishButton(

                        onClick = {

                            // TODO Save Event

                        }

                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    SaveDraftButton(

                        onClick = {

                            // TODO Save Draft

                        }

                    )

                    Spacer(modifier = Modifier.height(20.dp))

                }

            }

        }

    }

}