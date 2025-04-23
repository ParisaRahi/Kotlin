package com.example.parisara_oblig1.ui.unitconverter


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.parisara_oblig1.R
import kotlinx.coroutines.launch
import java.math.RoundingMode
import java.text.DecimalFormat


@OptIn( ExperimentalMaterial3Api::class)
@Composable
fun UnitConverterScreen(navController: NavController) {

    var text by remember { mutableStateOf("") }
    val options = stringArrayResource(id = R.array.Volume)

    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("") }

    var givenNumber by remember { mutableStateOf(0.00) }
    var result by remember { mutableStateOf(0.00) }

    val snackbarHostState = remember{ SnackbarHostState() }
    val coScope = rememberCoroutineScope() // et lettvekt tråd

    val keyboardController = LocalFocusManager.current

    Scaffold(
        snackbarHost =  { SnackbarHost(hostState = snackbarHostState)},
        content = {innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                Column(
                    modifier = Modifier.weight(1F)
                ) {
                    OutlinedTextField(
                        value = text,
                        onValueChange = { text = it },
                        label = {
                            Text(
                                "Write volume  ",
                                textAlign = TextAlign.Start,
                                fontWeight = FontWeight.Bold,
                                fontSize = 25.sp
                            )
                        },

                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        modifier = Modifier
                            .height(200.dp)
                            .padding(60.dp)
                    )
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded },
                        modifier = Modifier.padding(60.dp)
                    ) {
                        TextField(
                            modifier = Modifier.menuAnchor(),
                            readOnly = true,
                            value = selectedOption,
                            onValueChange = { },
                            label = { Text("options", fontSize = 25.sp, ) },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                            colors = ExposedDropdownMenuDefaults.textFieldColors(),
                        )
                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },

                        ) {
                            options.forEach { opt ->
                                DropdownMenuItem(

                                    text = { Text(opt, fontSize = 20.sp) },
                                    onClick = {
                                        selectedOption = opt
                                        expanded = false
                                    },
                                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                                )
                            }
                        }

                    }
                    //
                    Button(
                        onClick =  {
                            if(selectedOption.equals("") || text.equals("") || text.equals(text.toString())){
                                coScope.launch {
                                    snackbarHostState.showSnackbar(
                                        message = "You didn't select option and volume is either empty or string", selectedOption,
                                        duration = SnackbarDuration.Short

                                    )
                                }
                            }
                            else{
                                givenNumber = text.toDouble()
                                result = Convert(givenNumber, selectedOption, options)

                            }
                            keyboardController.clearFocus()

                        },
                        shape = RectangleShape,
                        modifier = Modifier
                            .padding(80.dp)
                            .height(80.dp)
                            .width(250.dp)
                    )
                    {
                        Text("Convert to liter", fontSize = 25.sp)
                    }
                    Box(
                        modifier = Modifier
                            .height(80.dp)
                            .width(420.dp).padding(50.dp, 10.dp)
                            .background(color = Color.White)
                            .border(2.dp, Color.LightGray, RectangleShape)
                    ){
                        Text(
                            text = "$result L",
                            fontSize = 30.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxSize()
                        )
                    }
                }
                Button(
                    onClick = { navController.navigate("QuizScreen")},
                    modifier = Modifier
                        .height(60.dp)
                        .fillMaxWidth(),
                        //.weight(1f),
                    shape = RectangleShape,
                ){
                    Text(" To QuizScreen",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.width(400.dp).height(60.dp),
                        fontSize = 25.sp
                    )
                }
            }
        }
    )
}


fun Convert(givenNumber: Double, selectedOption: String, options:Array<String>): Double{

    var result: Double = 0.00
    val toDesimal = DecimalFormat("#.##")

    if(selectedOption.equals(options[0])){
        result = givenNumber * 0.02957
    }
    else if(selectedOption.equals(options[1])){
        result = givenNumber * 0.23659
    }
    else if(selectedOption.equals(options[2])) {
        result = givenNumber * 3.78541
    }
    else if(selectedOption.equals(options[3])){
        result = givenNumber * 238.481

    }

    toDesimal.roundingMode = RoundingMode.FLOOR
    return toDesimal.format(result).toDouble()
}





