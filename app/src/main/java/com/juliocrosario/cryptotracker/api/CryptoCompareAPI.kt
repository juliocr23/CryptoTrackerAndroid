package com.juliocrosario.cryptotracker.api

import android.content.Context
import com.google.gson.Gson
import com.juliocrosario.cryptotracker.model.Cryptocurrency
import com.juliocrosario.cryptotracker.util.Utils
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CryptoCompareAPI {


    @GET("pricemulti" )
    suspend fun getPrices(@Query("tsyms") market:String, @Query("fsyms") cryptoRequest:String): ResponseBody

    @GET("all/coinlist" )
    suspend fun downloadCoinList():ResponseBody

    companion object {
        const val BASE_URL = "https://min-api.cryptocompare.com/data/"
        const val IMAGE_URL = "https://www.cryptocompare.com/"
    }
}