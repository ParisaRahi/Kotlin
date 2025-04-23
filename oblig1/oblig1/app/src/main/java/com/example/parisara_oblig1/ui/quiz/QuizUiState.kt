package com.example.parisara_oblig1.ui.quiz


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

data class QuizUiState(val quiz: Questions)

    @Composable
    fun QuizScreen(navController: NavController) {
        val questionList = mutableListOf(
            Questions("1.Walt Disney holds the record for det most Oscars?", true),
            Questions("2.Venus is the hottest planet in the solar system?", true),
            Questions("3.Spaghetto is the singular word for a piece of spaghetti?", true),
            Questions("4.Fish cannot blink?", true),
            Questions("5.Goldfish have a two second memory?", false)
        )
        var point by remember{mutableStateOf(0)}
        var quizCounter by remember{mutableStateOf(0)}
        var isVisible by remember{ mutableStateOf(true) }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally


        ) {
            Box(
                modifier = Modifier
                    .height(130.dp)
                    .width(700.dp)
                    .padding(50.dp, 10.dp)
                    .background(color = Color.White)
                    .border(2.dp, Color.LightGray, RectangleShape)
            ) {
                Text(
                    textAlign = TextAlign.Center,
                    text = Question(questionList,quizCounter ) ,
                    fontSize = 30.sp,
                    modifier = Modifier
                        .fillMaxSize()
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(//True
                    modifier = Modifier.height(80.dp).width(130.dp),
                    enabled = isVisible,
                    onClick = {

                        if (questionList[quizCounter].answer) {
                            point++
                        }

                        if(quizCounter < questionList.size){
                            quizCounter++
                        }

                        if(quizCounter == questionList.size){
                            isVisible = false
                        }


                    },
                    shape = RectangleShape,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Green)
                ) {
                    Text("True", fontSize = 30.sp, color = Color.Black)
                }

                Button(//False
                    modifier = Modifier.height(80.dp).width(130.dp),
                    enabled = isVisible,
                    onClick = {
                        if (!questionList[quizCounter].answer ) {
                            point++
                        }
                        if(quizCounter < questionList.size){
                            quizCounter++
                        }

                        if(quizCounter == questionList.size){
                            isVisible = false
                        }

                    },

                    shape = RectangleShape,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Text("False", fontSize = 30.sp, color = Color.Black)
                }

            }
            Box(
                modifier = Modifier
                    .padding(50.dp, 10.dp)
                    .height(80.dp)
                    .width(350.dp)
                    .background(color = Color.White)
                    .border(2.dp, Color.LightGray, RectangleShape)
            ) {
                Text(
                    text = "Point:[$point/$quizCounter]",
                    fontSize = 30.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxSize()
                )
            }

            Button(
                onClick = { navController.navigate("QuizScreen") },
                shape = RectangleShape,
            ) {
                Text(
                    " Restart quiz",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .width(400.dp)
                        .height(60.dp),
                    fontSize = 30.sp

                )

            }

        }

    }

fun Question(questionList: MutableList<Questions>, quizCounter: Int): String{
    if(quizCounter < questionList.size){
        return questionList[quizCounter].question
    }
    return "The quiz is finished! you can restart it."
}




