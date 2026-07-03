package org.ukrida.hmifukridamobile.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun StatusChip(status: String) {
    val colors = MaterialTheme.colorScheme

    val (background, textColor) = when (status) {
        "Approved", "Hadir" -> colors.tertiaryContainer to colors.onTertiaryContainer
        "Waiting", "Belum Hadir" -> colors.secondaryContainer to colors.onSecondaryContainer
        else -> colors.errorContainer to colors.onErrorContainer
    }

    Text(
        text = status,
        color = textColor,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .background(background, RoundedCornerShape(50))
            .padding(horizontal = 12.dp, vertical = 6.dp)
    )
}
