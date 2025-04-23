package com.example.parisara_oblig2.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.parisara_oblig2.data.DataSource
import com.example.parisara_oblig2.data.DistrictsTotalVotes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AlpacaViewModel: ViewModel() {

    private val path = "https://www.uio.no/studier/emner/matnat/ifi/IN2000/v23/obligatoriske-oppgaver/alpacaparties.json"
    private val dataSource = DataSource(path)

    private val _alpacaUiState = MutableStateFlow(
        AlpacaUiState(
            parties = listOf(), DistrictsTotalVotes(
               mapOf() , "", 0), mapOf()
        ))

    val alpacaUiState: StateFlow<AlpacaUiState> = _alpacaUiState.asStateFlow()

    init {
        getAlpacaObject()
    }

    private fun getAlpacaObject() {
        viewModelScope.launch(Dispatchers.IO) {
            val alpacaParties = dataSource.getResponse()
            val obj = DistrictsTotalVotes(mapOf(), "", 0)
            _alpacaUiState.value = AlpacaUiState(parties = alpacaParties,obj , mapOf())

        }
    }

     fun update(district: String) {
         viewModelScope.launch(Dispatchers.IO) {
             if (district == "District(1)") {
                 val dis1: DistrictsTotalVotes = dataSource.getDis1()
                 _alpacaUiState.value = _alpacaUiState.value.copy(
                     district = dis1,
                     votes = dis1.parties
                 )

             }
             if (district == "District(2)") {
                 val dis2: DistrictsTotalVotes = dataSource.getDis2()
                 _alpacaUiState.value = _alpacaUiState.value.copy(
                     district = dis2,
                     votes = dis2.parties,
                 )
             }
             if (district == "District(3)") {
                 val dis3: DistrictsTotalVotes = dataSource.getDis3()
                 _alpacaUiState.value = _alpacaUiState.value.copy(
                     district = dis3,
                     votes = dis3.parties,
                 )
             }
         }
     }
}
