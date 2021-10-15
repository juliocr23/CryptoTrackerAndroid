package com.juliocrosario.cryptotracker.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.juliocrosario.cryptotracker.R
import com.juliocrosario.cryptotracker.model.Cryptocurrency
import android.graphics.BitmapFactory

import android.graphics.Bitmap
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch
import java.net.URL


class CryptoAdapter(private val context: Context, private var cryptocurrencies:List<Cryptocurrency>):
  RecyclerView.Adapter<CryptoAdapter.ViewHolder>(){

     class ViewHolder(view: View): RecyclerView.ViewHolder(view){
      val nameTV: TextView     = view.findViewById(R.id.nameTV)
      val symbolTV: TextView   = view.findViewById(R.id.symbolTV)
      val priceTV: TextView    = view.findViewById(R.id.priceTV)
      val imageView: ImageView = view.findViewById(R.id.cryptoIV)
    }

    fun setData(list: List<Cryptocurrency>){
        cryptocurrencies = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      val view = LayoutInflater.from(parent.context).inflate(R.layout.assets_row, parent,false)
      return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
         holder.nameTV.text   = cryptocurrencies[position].name
         holder.symbolTV.text = cryptocurrencies[position].symbol
         holder.priceTV.text  = cryptocurrencies[position].price.toString()

         val url = cryptocurrencies[position].imageUrl
         Glide.with(context).load(url).into(holder.imageView)
    }
    override fun getItemCount() = cryptocurrencies.size
}