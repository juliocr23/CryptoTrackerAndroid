package com.juliocrosario.cryptotracker.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Cryptocurrency(

    @PrimaryKey val id:String,
    val name:String,
    val symbol:String,
    var description:String?,
    var algorithm: String?,
    var proofType: String?,
    var sortOrder: String?,
    var imageUrl: String?) {

    var change24H: Double? = null
    var highDay:   Double? = null
    var lowDay:    Double? = null
    var marketCap: Double? = null
    var supply:    Double? = null
    var volume24H: Double? = null

    var price:Double? = null
}



