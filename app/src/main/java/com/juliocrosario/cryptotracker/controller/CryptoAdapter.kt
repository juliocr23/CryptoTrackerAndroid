package com.juliocrosario.cryptotracker.controller

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.juliocrosario.cryptotracker.R
import com.juliocrosario.cryptotracker.model.Cryptocurrency

class CryptoAdapter(private val cryptocurrencies:ArrayList<Cryptocurrency>):
  RecyclerView.Adapter<CryptoAdapter.ViewHolder>(){

     class ViewHolder(view: View): RecyclerView.ViewHolder(view){
      val nameTV: TextView     = view.findViewById(R.id.nameTV)
      val symbolTV: TextView   = view.findViewById(R.id.symbolTV)
      val priceTV: TextView    = view.findViewById(R.id.priceTV)
      val imageView: ImageView = view.findViewById(R.id.cryptoIV)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      val view = LayoutInflater.from(parent.context).inflate(R.layout.assets_row, parent,false)
      return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
         holder.nameTV.text   = cryptocurrencies[position].name
         holder.symbolTV.text = cryptocurrencies[position].symbol
         holder.priceTV.text  = cryptocurrencies[position].price.toString()
    }

    override fun getItemCount() = cryptocurrencies.size
}