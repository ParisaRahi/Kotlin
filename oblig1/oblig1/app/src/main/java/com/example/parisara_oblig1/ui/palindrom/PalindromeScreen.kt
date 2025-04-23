package com.example.parisara_oblig1.ui.palindrom

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn( ExperimentalMaterial3Api::class)
@Composable
fun PalindromeScreen(navController: NavController) {

    var text by remember { mutableStateOf("") }//skal huske på hvor mange ganger variabelen er kalt
    var isPalindrome by remember { mutableStateOf(false) }
    var palindromeText by remember { mutableStateOf("") }
    val keyboardController = LocalFocusManager.current

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Column(
            modifier = Modifier.weight(1f)
        ){
            TextField(
                value = text,
                onValueChange = { text = it },
                label = {
                    Text(
                        "TextField",
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp
                    )
                },
                modifier = Modifier.padding(60.dp)
            )
            Button(
                onClick = {
                    isPalindrome = IsPalindrom(text)
                    if(isPalindrome){
                        palindromeText  = "The word is palindrome"

                    }
                    else{
                        palindromeText  = "The word is not palindrome"
                    }
                    text = ""
                    keyboardController.clearFocus()

                },

                shape = RectangleShape,
                modifier = Modifier
                    .padding(80.dp).width(300.dp).height(100.dp)

            )

            {
                Text(text = "Is palindrome?", textAlign = TextAlign.Center, fontSize = 25.sp)
            }


            Text(
                text = palindromeText,
                textAlign = TextAlign.Center,
                fontSize = 30.sp,
                modifier = Modifier.padding(80.dp).width(300.dp).height(70.dp)
            )

        }

        Button(
            onClick = { navController.navigate("UnitConverterScreen")},
            shape = RectangleShape,
        ){
            Text(" To UnitCoverterScreen",
                textAlign = TextAlign.Center,
                modifier = Modifier.width(400.dp).height(60.dp),
                fontSize = 25.sp

            )
        }

    }
}

fun IsPalindrom(text: String): Boolean {
    val reverseString = text.trim().reversed()
    return text.equals(reverseString, ignoreCase = true)
}