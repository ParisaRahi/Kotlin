package com.example.parisara_oblig2.data

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.gson.*
import java.io.InputStream


class DataSource(private val path: String) {

    private val dis1 = "https://in2000-proxy.ifi.uio.no/alpacaapi/district1"
    private val dis2 = "https://in2000-proxy.ifi.uio.no/alpacaapi/district2"
    private val dis3 = "https://in2000-proxy.ifi.uio.no/alpacaapi/district3"

    private val client = HttpClient {
        install(ContentNegotiation) {
            gson()
        }
    }

    suspend fun getResponse(): List<AlpacaParty> {
        val alpaca: AlpacaParties = client.get(path).body()
        return alpaca.parties
    }


    suspend fun getDis1(): DistrictsTotalVotes {
        val district1: List<Dis1> = client.get(dis1).body()
        var party1 = 0
        var party2 = 0
        var party3 = 0
        var party4 = 0
        for(item in district1){
            if(item.id == "1") party1++
            if(item.id == "2") party2++
            if(item.id == "3") party3++
            if(item.id == "4") party4++
        }
        //val list = listOf(party1, party2,party3,party4)
        val totalVotes = party1 + party2 + party3+ party4
        val parties = getResponse()
        val totalVoteForEveryParty = mapOf(parties[0] to party1, parties[1] to party2, parties[2] to party3, parties[3] to party4)
        return DistrictsTotalVotes(totalVoteForEveryParty, "District(1)", totalVotes)
    }
        

    suspend fun getDis2(): DistrictsTotalVotes {
        val district2: List<Dis2> = client.get(dis2).body()
        var party1 = 0
        var party2 = 0
        var party3 = 0
        var party4 = 0
        for(item in district2){
            if(item.id == "1") party1++
            if(item.id == "2") party2++
            if(item.id == "3") party3++
            if(item.id == "4") party4++
        }
        //val list = listOf(party1, party2,party3,party4)
        val totalVotes = party1 + party2 + party3+ party4
        val parties = getResponse()
        val totalVoteForEveryParty = mapOf(parties[0] to party1, parties[1] to party2, parties[2] to party3, parties[3] to party4)
        return DistrictsTotalVotes(totalVoteForEveryParty, "District(2)", totalVotes)
    }

    suspend fun getDis3(): DistrictsTotalVotes {
        val district3: String = client.get(dis3).body()
        val inputStream: InputStream = district3.byteInputStream()
        val listOfParties = XMLParser().parse(inputStream)
        var party1 = 0
        var party2 = 0
        var party3 = 0
        var party4 = 0
        for(item in listOfParties){
            if(item.id == 1) party1 = item.votes
            if(item.id == 2) party2 = item.votes
            if(item.id == 3) party3 = item.votes
            if(item.id == 4) party4 = item.votes
        }
        //val list = listOf(party1, party2,party3,party4)
        val totalVotes = party1 + party2 + party3 + party4
        val parties = getResponse()
        val totalVoteForEveryParty = mapOf(parties[0] to party1, parties[1] to party2, parties[2] to party3, parties[3] to party4)
        return DistrictsTotalVotes(totalVoteForEveryParty,"District(3)", totalVotes)
    }
}