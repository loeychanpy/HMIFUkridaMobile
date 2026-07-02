package org.ukrida.hmifukridamobile.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddPhotoAlternate
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun UploadBannerCard(

    onClick: () -> Unit = {}

) {

    Column {

        Text(

            text = "Event Banner *",

            style = MaterialTheme.typography.titleSmall

        )

        Spacer(modifier = Modifier.height(10.dp))

        Box(

            modifier = Modifier
                .fillMaxWidth()
                .height(170.dp)
                .border(
                    BorderStroke(
                        1.5.dp,
                        Color(0xFF1565C0)
                    ),
                    RoundedCornerShape(14.dp)
                )
                .clickable {

                    onClick()

                },

            contentAlignment = Alignment.Center

        ) {

            Column(

                horizontalAlignment = Alignment.CenterHorizontally

            ) {

                Icon(

                    imageVector = Icons.Outlined.AddPhotoAlternate,

                    contentDescription = null,

                    tint = Color(0xFF1565C0),

                    modifier = Modifier.size(48.dp)

                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(

                    text = "Upload Banner",

                    color = Color(0xFF1565C0)

                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(

                    text = "PNG / JPG (Max 5 MB)",

                    color = Color.Gray,

                    style = MaterialTheme.typography.bodySmall

                )

            }

        }

    }

}