package org.ukrida.hmifukridamobile.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun DatePickerField(

    modifier: Modifier = Modifier,

    value: String,

    onValueChange: (String) -> Unit

) {

    Text(

        text = "Date *",

        style = MaterialTheme.typography.titleSmall

    )

    OutlinedTextField(

        modifier = modifier.fillMaxWidth(),

        value = value,

        onValueChange = onValueChange,

        placeholder = {

            Text("Choose Date")

        },

        leadingIcon = {

            Icon(

                Icons.Default.DateRange,

                null

            )

        },

        singleLine = true

    )

}