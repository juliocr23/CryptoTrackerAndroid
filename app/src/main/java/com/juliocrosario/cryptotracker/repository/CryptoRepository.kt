package com.juliocrosario.cryptotracker.repository

import androidx.lifecycle.LiveData
import com.juliocrosario.cryptotracker.api.RetrofitInstance
import com.juliocrosario.cryptotracker.model.Cryptocurrency
import com.juliocrosario.cryptotracker.model.CryptocurrencyDao
import okhttp3.ResponseBody

class CryptoRepository(private val cryptocurrencyDao: CryptocurrencyDao) {

    //MARK: DB methods
    suspend fun saveCryptos(list:List<Cryptocurrency>){
        cryptocurrencyDao.saveCryptos(list)
    }
    suspend fun readAllData(): List<Cryptocurrency> = cryptocurrencyDao.readAllData()

    suspend fun updateCryptos(list: List<Cryptocurrency>){
        cryptocurrencyDao.updateCryptos(list)
    }

    fun searchCryptos(searchQuery: String): LiveData<List<Cryptocurrency>>{
       return cryptocurrencyDao.searchCryptos(searchQuery)
    }

    suspend fun deleteCrypto(cryptocurrency: Cryptocurrency){
        cryptocurrencyDao.deleteCrypto(cryptocurrency)
    }

    suspend fun deleteAll(){
        cryptocurrencyDao.deleteAll()
    }

    //MARK: Crypto-Compare
    suspend fun getPrices(market:String, cryptoRequest:String):ResponseBody {
       return RetrofitInstance.api.getPrices(market, cryptoRequest)
    }
    suspend fun downloadCoinList():ResponseBody{
        return  RetrofitInstance.api.downloadCoinList()
    }
}