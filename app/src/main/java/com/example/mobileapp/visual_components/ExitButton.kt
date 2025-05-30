package com.example.mobileapp.visual_components

import android.content.Intent
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobileapp.R
import com.example.mobileapp.ui.theme.*

@Composable
fun ExitButton(){
    val context = LocalContext.current
    Button(
        onClick = {
            val homeIntent = Intent(Intent.ACTION_MAIN).apply {
                addCategory(Intent.CATEGORY_HOME)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(homeIntent)
        },
        modifier = Modifier
            .width(275.dp)
            .height(50.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = ExitAndHomeButtonColor,
        )
    ) {
        Text(stringResource(R.string.exit), fontSize = 24.sp, color = White)
    }
}