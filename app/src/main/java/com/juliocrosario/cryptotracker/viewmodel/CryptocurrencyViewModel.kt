package com.juliocrosario.cryptotracker.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.juliocrosario.cryptotracker.api.CryptoCompareAPI.Companion.IMAGE_URL
import com.juliocrosario.cryptotracker.model.Cryptocurrency
import com.juliocrosario.cryptotracker.model.CryptocurrencyDB
import com.juliocrosario.cryptotracker.repository.CryptoRepository
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class CryptocurrencyViewModel(application: Application): AndroidViewModel(application) {

    val output: MutableLiveData<List<Cryptocurrency>> = MutableLiveData()
    private val market:String = "USD"

    private  var assets: HashMap<String,Cryptocurrency> = HashMap()
    private  var requestQueue:Queue<Cryptocurrency> = LinkedList()
    private  var repository: CryptoRepository

    init {
        val cryptocurrencyDao = CryptocurrencyDB.getDatabase(application).cryptocurrencyDao()
        repository = CryptoRepository(cryptocurrencyDao)
    }

    private suspend fun loadData(){

        var cryptoList = repository.readAllData()
        if(cryptoList.isEmpty()){
            val coinList = repository.downloadCoinList()
            cryptoList = parseCoinList(coinList.string())
        }else{
            output.postValue(cryptoList)
        }

        requestQueue.addAll(cryptoList)
        for(crypto in cryptoList) {
             assets[crypto.symbol] = crypto
         }
    }

    fun getCryptos(){

        viewModelScope.launch {

            loadData()
            val temp = ArrayList<Cryptocurrency>()
            while(requestQueue.isNotEmpty()){

                val request  = getRequest()
                val response = repository.getPrices(market,request)
                val jsonObject = JSONObject(response.string())

                val processedAssets = ArrayList<Cryptocurrency>()
                for(key in jsonObject.keys()){

                    assets[key]?.price = jsonObject.getJSONObject(key).optDouble(market)

                    if(assets[key] != null && assets[key]!!.price != null) {
                        processedAssets.add(assets[key]!!)
                        temp.add(assets[key]!!)
                    }
                }
                output.postValue(processedAssets)
            }

            if(repository.readAllData().isNullOrEmpty()){
                repository.saveCryptos(temp)
            }else{
                repository.updateCryptos(temp)
            }
        }
    }

    private fun getRequest():String{

        var output = ""
        while (requestQueue.isNotEmpty() &&
              (output.length + requestQueue.peek().symbol.length < 300)){
            output += requestQueue.remove().symbol + ","
        }
        return output
    }



    private fun parseCoinList(list:String):List<Cryptocurrency>{
        val jsonObject = JSONObject(list)
        val data = jsonObject.optJSONObject("Data")
        val output = ArrayList<Cryptocurrency>()

        for(key in data.keys()){

            val temp = data[key] as JSONObject

            val crypto = Cryptocurrency(temp.optString("Id"),
                                        temp.optString("Name"),
                                        temp.optString("Symbol"),
                                        temp.optString("Description"),
                                        temp.optString("Algorithm"),
                                        temp.optString("ProofType"),
                                        temp.optString("SortOrder"),
                                IMAGE_URL + temp.optString("ImageUrl")
                )
            output.add(crypto)
        }
        return output
    }

}