package com.juliocrosario.cryptotracker.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.juliocrosario.cryptotracker.adapter.CryptoAdapter
import com.juliocrosario.cryptotracker.databinding.ActivityMainBinding
import com.juliocrosario.cryptotracker.model.Cryptocurrency
import com.juliocrosario.cryptotracker.viewmodel.CryptocurrencyViewModel
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var cryptoViewModel: CryptocurrencyViewModel
    private lateinit var adapter: CryptoAdapter
    private var hashSet: HashSet<Cryptocurrency>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        cryptoViewModel = ViewModelProvider(this).get(CryptocurrencyViewModel::class.java)

        cryptoViewModel.getCryptos()

        cryptoViewModel.output.observe(this, androidx.lifecycle.Observer { response ->

            if(hashSet.isNullOrEmpty()){
                hashSet = HashSet()
                hashSet!!.addAll(response)
                listView()
            }else {

                if(hashSet!!.containsAll(response)){
                    hashSet!!.removeAll(response)
                }
                hashSet!!.addAll(response)
                adapter.notifyDataSetChanged()
            }
            Log.e("Size ", hashSet!!.size.toString())
        })
    }

    private fun listView(){
        binding.recyclerViewCrypto.layoutManager = LinearLayoutManager(this)
        adapter = CryptoAdapter(this,hashSet!!.toList())
        binding.recyclerViewCrypto.adapter = adapter
    }

}