package com.example.parisara_oblig1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.parisara_oblig1.ui.palindrom.PalindromeScreen
import com.example.parisara_oblig1.ui.quiz.*
import com.example.parisara_oblig1.ui.quiz.QuizUiState
import com.example.parisara_oblig1.ui.theme.Parisara_oblig1Theme
import com.example.parisara_oblig1.ui.unitconverter.UnitConverterScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Parisara_oblig1Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val navController = rememberNavController()
                    NavHost(navController =navController, startDestination = "palindromeScreen"){
                        composable("palindromeScreen"){ PalindromeScreen(navController)}
                        composable ("unitconverterScreen"){ UnitConverterScreen(navController)}
                        composable("quizScreen"){ QuizScreen(navController)}
                    }
                }
            }
        }
    }
}

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController =navController, startDestination = "Palindrom"){
        composable("PalindromeScreen"){ PalindromeScreen(navController)}
        composable ("UnitConverterScreen"){ UnitConverterScreen(navController)}
        composable("QuizState"){ QuizScreen(navController)}
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Parisara_oblig1Theme {
        Navigation()
    }
}