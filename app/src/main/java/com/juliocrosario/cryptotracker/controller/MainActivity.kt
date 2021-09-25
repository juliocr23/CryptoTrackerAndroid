package com.juliocrosario.cryptotracker.controller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.juliocrosario.cryptotracker.R
import com.juliocrosario.cryptotracker.databinding.ActivityMainBinding
import com.juliocrosario.cryptotracker.model.Cryptocurrency
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var list = ArrayList<Cryptocurrency>(
       listOf(Cryptocurrency("", "Bitcoin", "BTC", 5000.0))
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        listView()
    }

    private fun listView(){
        binding.recyclerViewCrypto.layoutManager = LinearLayoutManager(this)
        val cryptoAdapter = CryptoAdapter(list)
        binding.recyclerViewCrypto.adapter = cryptoAdapter
    }

}