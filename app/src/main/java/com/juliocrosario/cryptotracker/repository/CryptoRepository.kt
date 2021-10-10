package com.juliocrosario.cryptotracker.repository

import com.juliocrosario.cryptotracker.api.RetrofitInstance
import com.juliocrosario.cryptotracker.model.Cryptocurrency
import com.juliocrosario.cryptotracker.model.CryptocurrencyDao
import okhttp3.ResponseBody

class CryptoRepository(private val cryptocurrencyDao: CryptocurrencyDao) {

    suspend fun saveCryptos(list:List<Cryptocurrency>){
        cryptocurrencyDao.saveCryptos(list)
    }
    suspend fun readAllData(): List<Cryptocurrency> = cryptocurrencyDao.readAllData()

    suspend fun getPrices(market:String, cryptoRequest:String):ResponseBody {
       return RetrofitInstance.api.getPrices(market, cryptoRequest)
    }
    suspend fun downloadCoinList():ResponseBody{
        return  RetrofitInstance.api.downloadCoinList()
    }
}