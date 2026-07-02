package org.ukrida.hmifukridamobile.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun StatusChip(
    status: String
) {
    val (background, textColor) = when (status) {
        "Approved", "Hadir" -> Color(0xFF00C853).copy(alpha = 0.15f) to Color(0xFF00C853)
        "Waiting", "Belum Hadir" -> Color(0xFFFFAB00).copy(alpha = 0.15f) to Color(0xFFFFAB00)
        else -> Color(0xFFFF1744).copy(alpha = 0.15f) to Color(0xFFFF1744)
    }

    Text(
        text = status,
        color = textColor,
        style = MaterialTheme.typography.labelSmall,
        modifier = Modifier
            .background(
                background,
                RoundedCornerShape(8.dp)
            )
            .padding(
                horizontal = 12.dp,
                vertical = 6.dp
            )
    )
}
