package org.ukrida.hmifukridamobile.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun PublishButton(

    onClick: () -> Unit

) {

    Button(

        modifier = Modifier
            .fillMaxWidth()
            .height(54.dp),

        onClick = onClick,

        colors = ButtonDefaults.buttonColors(

            containerColor = Color(0xFF1565C0)

        )

    ) {

        Text(

            text = "Publish Event"

        )

    }

}