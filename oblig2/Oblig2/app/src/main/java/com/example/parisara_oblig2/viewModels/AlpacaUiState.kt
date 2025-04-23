package com.example.parisara_oblig2.viewModels

import com.example.parisara_oblig2.data.AlpacaParty
import com.example.parisara_oblig2.data.DistrictsTotalVotes

data class AlpacaUiState(
    val parties: List<AlpacaParty>,
    val district: DistrictsTotalVotes,
    val votes: Map<AlpacaParty,Int>,
)