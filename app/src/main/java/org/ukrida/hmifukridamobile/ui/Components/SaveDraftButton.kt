package org.ukrida.hmifukridamobile.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SaveDraftButton(

    onClick: () -> Unit

) {

    OutlinedButton(

        modifier = Modifier
            .fillMaxWidth()
            .height(54.dp),

        onClick = onClick,

        colors = ButtonDefaults.outlinedButtonColors(

            contentColor = Color(0xFF1565C0)

        )

    ) {

        Text(

            text = "Save Draft"

        )

    }

}