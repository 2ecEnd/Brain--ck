package com.example.mobileapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.unit.dp

@Composable
fun DeclareVariableBlock(){
    Card(
        modifier = Modifier
            .width(200.dp)
            .height(48.dp)
            .background(Color(red = 255, green = 128, blue = 0)),
        shape = RoundedCornerShape(15.dp)
    )
    {

    }
}