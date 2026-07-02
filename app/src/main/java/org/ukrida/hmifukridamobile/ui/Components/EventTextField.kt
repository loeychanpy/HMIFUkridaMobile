package org.ukrida.hmifukridamobile.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun EventTextField(

    title: String,

    value: String,

    onValueChange: (String) -> Unit

) {

    Text(

        text = "$title *",

        style = MaterialTheme.typography.titleSmall

    )

    OutlinedTextField(

        modifier = Modifier.fillMaxWidth(),

        value = value,

        onValueChange = onValueChange,

        placeholder = {

            Text(title)

        },

        singleLine = true

    )

}