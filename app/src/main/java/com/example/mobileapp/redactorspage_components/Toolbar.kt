package com.example.mobileapp.redactorspage_components


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mobileapp.R
import com.example.mobileapp.visual_components.HomeButton

@Composable
fun Toolbar(navController: NavController){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.08f)
            .background(Color(red = 230, green = 224, blue = 233)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    )
    {
        HomeButton(navController)

        Button(
            onClick = {},
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
                painter = painterResource(id = R.drawable.gear_128px),
                contentDescription = stringResource(R.string.icon),
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Fit,
            )
        }

        Button(
            onClick = {},
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
                painter = painterResource(id = R.drawable.cloud_arrow_down_128px),
                contentDescription = stringResource(R.string.icon),
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Fit,
            )
        }

        Button(
            onClick = {},
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
                painter = painterResource(id = R.drawable.person_gear_128px),
                contentDescription = stringResource(R.string.icon),
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Fit,
            )
        }
    }
}