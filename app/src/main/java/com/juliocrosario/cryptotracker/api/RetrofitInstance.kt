package com.juliocrosario.cryptotracker.api


import com.google.gson.GsonBuilder
import com.juliocrosario.cryptotracker.api.CryptoCompareAPI.Companion.BASE_URL
import com.juliocrosario.cryptotracker.model.Cryptocurrency
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val api:CryptoCompareAPI by lazy {
        retrofit.create(CryptoCompareAPI::class.java)
    }
}