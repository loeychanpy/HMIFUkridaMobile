package org.ukrida.hmifukridamobile.ui.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DashboardStatCard(

    title: String,

    value: String,

    modifier: Modifier = Modifier

) {

    Card(

        modifier = modifier,

        elevation = CardDefaults.cardElevation(8.dp),

        colors = CardDefaults.cardColors(

            containerColor = Color(0xFF1565C0)

        )

    ) {

        Column(

            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),

            verticalArrangement = Arrangement.Center

        ) {

            Text(

                text = value,

                fontSize = 30.sp,

                fontWeight = FontWeight.Bold,

                color = Color.White

            )

            Text(

                text = title,

                style = MaterialTheme.typography.bodyMedium,

                color = Color.White.copy(alpha = 0.85f)

            )

        }

    }

}