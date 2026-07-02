package org.ukrida.hmifukridamobile.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun TimePickerField(

    modifier: Modifier = Modifier,

    value: String,

    onValueChange: (String) -> Unit

) {

    Text(

        text = "Time *",

        style = MaterialTheme.typography.titleSmall

    )

    OutlinedTextField(

        modifier = modifier.fillMaxWidth(),

        value = value,

        onValueChange = onValueChange,

        placeholder = {

            Text("Choose Time")

        },

        leadingIcon = {

            Icon(

                Icons.Default.Schedule,

                null

            )

        },

        singleLine = true

    )

}