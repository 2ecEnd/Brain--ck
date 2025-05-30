package com.example.mobileapp.visual_components


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mobileapp.R

@Composable
fun HomeButton(navController: NavController){
    val homeText = stringResource(R.string.home)

    Button(
        onClick = {navController.navigate(homeText)},
        modifier = Modifier
            .width(48.dp)
            .height(48.dp),
        shape = RoundedCornerShape(12.dp),
        contentPadding = PaddingValues(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(red = 103, green = 80, blue = 164),
        )
    )
    {
        Image(
            painter = painterResource(id = R.drawable.house_128px),
            contentDescription = stringResource(R.string.icon),
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Fit,
        )
    }
}