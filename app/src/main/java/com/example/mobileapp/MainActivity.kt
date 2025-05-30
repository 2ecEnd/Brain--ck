package com.example.mobileapp


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.mobileapp.ui.theme.MobileAppTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mobileapp.pages.CreateProjectPage
import com.example.mobileapp.pages.HomePage
import com.example.mobileapp.pages.ProjectsPage
import com.example.mobileapp.pages.RedactorPage


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            MobileAppTheme {

                val windowInsetsController = WindowInsetsControllerCompat(window, window.decorView)
                windowInsetsController.apply {
                    hide(WindowInsetsCompat.Type.navigationBars())
                    hide(WindowInsetsCompat.Type.statusBars())
                    systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                }

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
    val pagesName = listOf(
        stringResource(R.string.home),
        stringResource(R.string.projects),
        stringResource(R.string.createProject),
        stringResource(R.string.redactor)
    )
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = pagesName[3]) {
        composable(pagesName[0]) {
            HomePage(navController)
        }
        composable(pagesName[1]) {
            ProjectsPage(navController)
        }
        composable(pagesName[2]) {
            CreateProjectPage(navController)
        }
        composable(pagesName[3]) {
            RedactorPage(navController)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    App()
}