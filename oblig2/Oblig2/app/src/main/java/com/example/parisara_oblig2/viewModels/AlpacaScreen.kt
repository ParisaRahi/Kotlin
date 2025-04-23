package com.example.parisara_oblig2.viewModels


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.parisara_oblig2.R
import com.example.parisara_oblig2.data.AlpacaParty

@Composable
fun AlpacaScreen(alpacaViewModel:  AlpacaViewModel = viewModel()) {
    val alpacaUiState by alpacaViewModel.alpacaUiState.collectAsState()
    AlpacaPartiesView(alpacaUiState, alpacaViewModel)
}
@Composable
fun AlpacaPartiesView(parties: AlpacaUiState, alpacaViewModel:  AlpacaViewModel ){
    Column(
        modifier = Modifier
            .padding()
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        DropDownMenu(alpacaViewModel)
        LazyColumn{
            items(parties.parties){alpacaData ->
                //val percent = calcPercent(parties.votes, alpacaData.id)
                AlpacaCard(alpacaData = alpacaData, parties)
            }
        }
    }
}

/*fun calcPercent(votes: List<Int>, id: String) : Float {
    // {12, 14, 6, 4}
    //  1   2  3  4

    // total = ...
    var total: Float = 0.0F
    for(item in votes){
        total += item
    }
    votes.get(id.toInt())
    var percent:Float  = votes[id.toInt()]/total
    // percent = votes[id.toInt()-1] / total
    return percent
}*/


@Composable
fun AlpacaCard(alpacaData: AlpacaParty, uiState: AlpacaUiState){
    val partyVotes = (uiState.district.parties[alpacaData])?.times(100)
    val totalVotes = (uiState.district.totalVotes)
    val percent = partyVotes?.div(totalVotes)

    Card (
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ){
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(
                modifier = Modifier
                    .height(50.dp)
                    .width(400.dp)
                    .background(color = Color(alpacaData.color.toColorInt()))
            )
            Text(
                text = alpacaData.name,
                textAlign = TextAlign.Center,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )
            AsyncImage(
                model = alpacaData.img,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape)
            )
            Text(
                text = "Leader: ${alpacaData.leader}",
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = "Votes: ${uiState.district.parties[alpacaData]} - $percent % ",

                textAlign = TextAlign.Center,
                fontSize = 23.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownMenu(viewModel: AlpacaViewModel){
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("") }
    val options = stringArrayResource(id = R.array.Districts)

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = Modifier.padding(20.dp)
    ) {
        TextField(
            modifier = Modifier.menuAnchor(),
            readOnly = true,
            value = selectedOption,
            onValueChange = { },
            label = { Text("options", fontSize = 20.sp) },
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
                        viewModel.update(selectedOption)
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }
}



