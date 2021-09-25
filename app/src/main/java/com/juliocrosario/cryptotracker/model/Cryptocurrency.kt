package com.juliocrosario.cryptotracker.model

class Cryptocurrency(val id:String,val name:String, val symbol:String, var price:Double) {

    var change24H: Double? = null
    var highDay:   Double? = null
    var lowDay:    Double? = null
    var marketCap: Double? = null
    var supply:    Double? = null
    var volume24H: Double? = null

    //Icon
    var imgData: Int? = null
}