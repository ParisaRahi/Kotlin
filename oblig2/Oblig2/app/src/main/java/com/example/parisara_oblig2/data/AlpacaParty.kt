package com.example.parisara_oblig2.data

data class AlpacaParty(
    val id: String,
    val name: String,
    val leader: String,
    val img: String,
    val color: String,

)

data class AlpacaParties(
    val parties: List<AlpacaParty>
)

data class Dis1(
    val id: String
)

data class Dis2(
    val id: String
)

data class Dis3(
    val id: Int,
    val votes: Int
)

data class DistrictsTotalVotes(
    val parties: Map<AlpacaParty, Int>,
    val districtName: String,
    val totalVotes: Int
)


