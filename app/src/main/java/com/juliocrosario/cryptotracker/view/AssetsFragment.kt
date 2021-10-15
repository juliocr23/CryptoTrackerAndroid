package com.juliocrosario.cryptotracker.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.SearchView
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.juliocrosario.cryptotracker.R
import com.juliocrosario.cryptotracker.adapter.CryptoAdapter
import com.juliocrosario.cryptotracker.databinding.FragmentAssetsBinding
import com.juliocrosario.cryptotracker.model.Cryptocurrency
import com.juliocrosario.cryptotracker.viewmodel.CryptocurrencyViewModel

class AssetsFragment :Fragment(R.layout.fragment_assets) {

    private var binding: FragmentAssetsBinding? = null
    private lateinit var cryptoViewModel: CryptocurrencyViewModel
    private lateinit var adapter: CryptoAdapter
    private var hashSet: HashSet<Cryptocurrency>? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentAssetsBinding.bind(view)
        this.binding = binding

        setSearchBar()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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


    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    private fun setSearchBar(){


        binding?.searchBar?.searchBar?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

               if(start >= 0) {
                   binding!!.searchBar.searchIcon.tag = R.drawable.cancel
                   binding!!.searchBar.searchIcon.setImageResource(R.drawable.cancel)
               }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchDatabase(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {
                if(s != null && s.isEmpty()){
                    binding!!.searchBar.searchIcon.tag = R.drawable.drop_down
                    binding!!.searchBar.searchIcon.setImageResource(R.drawable.drop_down)

                }
            }
        })
        setSearchCancelListener()
    }


    private fun setSearchCancelListener(){
        binding!!.searchBar.searchIcon.setOnClickListener {
            val tag = binding?.searchBar?.searchIcon?.tag as Int
            if(  tag == R.drawable.cancel)
                binding?.searchBar?.searchBar?.setText("")
        }
    }
    private fun listView(){
        binding!!.recyclerViewCrypto.layoutManager = LinearLayoutManager(requireContext())
        adapter = CryptoAdapter(requireContext(),hashSet!!.toList())
        binding!!.recyclerViewCrypto.adapter = adapter
    }

    private fun searchDatabase(query: String){
        val searchQuery = "%$query%"

        cryptoViewModel.searchDatabase(searchQuery).observe(this, {list ->
               adapter.setData(list)
               adapter.notifyDataSetChanged()
        })
    }
}