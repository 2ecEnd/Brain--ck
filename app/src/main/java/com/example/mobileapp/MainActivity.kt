package com.example.mobileapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mobileapp.ui.theme.MobileAppTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MobileAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    App(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun App(modifier: Modifier = Modifier){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomePage(navController)
        }
        composable("projects") {
            ProjectsPage(navController)
        }
        composable("createProject") {
            CreateProjectPage(navController)
        }
    }
}

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
        modifier = Modifier.width(200.dp)
    )
    {
        Text("Выйти")
    }
}

@Composable
fun BackgroundImage(){
    Image(
        painter = painterResource(id = R.drawable.background),
        contentDescription = "Background",
        modifier = Modifier.fillMaxSize(),
    )
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    App()
}