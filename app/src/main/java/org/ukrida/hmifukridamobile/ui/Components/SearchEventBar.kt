package org.ukrida.hmifukridamobile.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SearchEventBar(

    value: String,

    onValueChange: (String) -> Unit

) {

    OutlinedTextField(

        modifier = Modifier.fillMaxWidth(),

        value = value,

        onValueChange = onValueChange,

        placeholder = {

            Text("Search Event")

        },

        leadingIcon = {

            Icon(

                Icons.Default.Search,

                null

            )

        },

        singleLine = true

    )

}