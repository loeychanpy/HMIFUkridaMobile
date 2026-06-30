package org.ukrida.hmifukridamobile.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryDropdown(

    value: String,

    onSelected: (String) -> Unit

) {

    val categoryList = listOf(

        "Seminar",

        "Workshop",

        "Competition",

        "Talkshow",

        "Webinar"

    )

    var expanded by remember {

        mutableStateOf(false)

    }

    Text(

        text = "Category *",

        style = MaterialTheme.typography.titleSmall

    )

    ExposedDropdownMenuBox(

        expanded = expanded,

        onExpandedChange = {

            expanded = !expanded

        }

    ) {

        OutlinedTextField(

            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),

            value = value,

            onValueChange = {},

            readOnly = true,

            placeholder = {

                Text("Select Category")

            },

            trailingIcon = {

                Icon(

                    Icons.Default.KeyboardArrowDown,

                    null

                )

            }

        )

        ExposedDropdownMenu(

            expanded = expanded,

            onDismissRequest = {

                expanded = false

            }

        ) {

            categoryList.forEach {

                DropdownMenuItem(

                    text = {

                        Text(it)

                    },

                    onClick = {

                        onSelected(it)

                        expanded = false

                    }

                )

            }

        }

    }

}