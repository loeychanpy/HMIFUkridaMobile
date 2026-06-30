package org.ukrida.hmifukridamobile.ui.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun StatusChip(

    status: String

) {

    val background = when(status){

        "Approved" -> Color(0xFFDFF5E1)

        "Waiting" -> Color(0xFFFFF4D6)

        else -> Color(0xFFFFE0E0)

    }

    val textColor = when(status){

        "Approved" -> Color(0xFF2E7D32)

        "Waiting" -> Color(0xFFF9A825)

        else -> Color.Red

    }

    Text(

        text = status,

        color = textColor,

        fontWeight = FontWeight.Bold,

        modifier = Modifier

            .background(

                background,

                RoundedCornerShape(50)

            )

            .padding(

                horizontal = 12.dp,

                vertical = 6.dp

            )

    )

}
