package com.example.mobileapp

import android.R.string
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.*
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun HomePage(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize()){
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = "background",
        )

        Card(
            modifier = Modifier
                .align(Alignment.Center)
                .width(325.dp)
                .height(200.dp),
            shape = RoundedCornerShape(15.dp)
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
                        onClick = {navController.navigate("projects")},
                        modifier = Modifier
                            .width(275.dp)
                            .height(50.dp)
                    )
                    {
                        Text("Проекты", fontSize = 24.sp)
                    }

                    ExitButton()
                }
            }
        }
    }
}

@Composable
fun ProjectsPage(navController: NavController){
    Box(modifier = Modifier.fillMaxSize().background(Color.Green)){
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = "background",
        )

        HomeButton(navController)

        Card(
            modifier = Modifier
                .align(Alignment.Center)
                .width(325.dp)
                .height(200.dp),
            shape = RoundedCornerShape(15.dp)
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
                        onClick = {navController.navigate("createProject")},
                        modifier = Modifier
                            .width(275.dp)
                            .height(50.dp)
                    )
                    {
                        Text("+", fontSize = 24.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun CreateProjectPage(navController: NavController){
    Box(modifier = Modifier.fillMaxSize().background(Color.Green)){
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = "background",
        )

        HomeButton(navController)

        Card(
            modifier = Modifier
                .align(Alignment.Center)
                .width(325.dp)
                .height(200.dp),
            shape = RoundedCornerShape(15.dp)
        )
        {
            ProjectNameForm(navController)
        }
    }
}

@Composable
fun ProjectNameForm(navController: NavController){
    var text by remember{ mutableStateOf("") }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
        )
        {
            TextField(
                modifier = Modifier
                    .width(275.dp)
                    .height(80.dp),
                value = text,
                onValueChange = { newText -> text = newText },
                textStyle = LocalTextStyle.current.copy(fontSize = 24.sp)
            )

            Button(
                onClick = {navController.navigate("redactor")},
                modifier = Modifier
                    .width(275.dp)
                    .height(50.dp)
            )
            {
                Text("Создать", fontSize = 24.sp)
            }
        }
    }
}

@Composable
fun RedactorPage(navController: NavController){
    Box(modifier = Modifier.fillMaxSize().background(Color(red = 249, green = 249, blue = 249))){
        Column(
            modifier = Modifier
                .fillMaxSize()
        )
        {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.1f)
                    .background(Color(red = 230, green = 224, blue = 233))
            )
            {
                HomeButton(navController)
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.75f)
                    .background(Color(red = 249, green = 249, blue = 249))
            )
            {

            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(1f)
                    .background(Color(red = 230, green = 224, blue = 233))
            )
            {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                )
                {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.33f)
                    )
                    {
                        DeclareVariableBlock()
                    }
                }
            }
        }
    }
}