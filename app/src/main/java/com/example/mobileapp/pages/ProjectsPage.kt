package com.example.mobileapp.pages


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mobileapp.R
import com.example.mobileapp.visual_components.HomeButton
import com.example.mobileapp.ui.theme.*

@Composable
fun ProjectsPage(navController: NavController){
    val createProjectsText = stringResource(R.string.createProject)

    Box(modifier = Modifier
        .fillMaxSize()){
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = stringResource(R.string.background),
        )

        HomeButton(navController)

        Card(
            modifier = Modifier
                .align(Alignment.Center)
                .width(325.dp)
                .height(200.dp),
            shape = RoundedCornerShape(15.dp),
            colors = CardDefaults.cardColors(containerColor = TabsBackground)
        )
        {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                Column(
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                )
                {
                    Button(
                        onClick = {navController.navigate(createProjectsText)},
                        modifier = Modifier
                            .width(275.dp)
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = NavButtonColor,
                        )
                    )
                    {
                        Text(stringResource(R.string.plus), fontSize = 24.sp, color = White)
                    }
                }
            }
        }
    }
}