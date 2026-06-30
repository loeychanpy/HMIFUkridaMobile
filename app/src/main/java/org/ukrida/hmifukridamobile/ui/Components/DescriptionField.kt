package org.ukrida.hmifukridamobile.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DescriptionField(

    value: String,

    onValueChange: (String) -> Unit

) {

    Text(

        text = "Description *",

        style = MaterialTheme.typography.titleSmall

    )

    OutlinedTextField(

        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp),

        value = value,

        onValueChange = onValueChange,

        placeholder = {

            Text(
                "Write event description..."
            )

        },

        maxLines = 6

    )

}