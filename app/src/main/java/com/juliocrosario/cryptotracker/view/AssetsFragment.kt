package com.juliocrosario.cryptotracker.view

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.SearchView
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
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
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

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.menu, menu)
//        val search = menu.findItem(R.id.appSearchBar)
//        val searchView = search.actionView as SearchView
//        searchView.queryHint = "Search"
//        searchView.isSubmitButtonEnabled = true
//        searchView.setOnQueryTextListener(this)
//        super.onCreateOptionsMenu(menu, inflater)
//    }

    private fun listView(){
        binding!!.recyclerViewCrypto.layoutManager = LinearLayoutManager(requireContext())
        adapter = CryptoAdapter(requireContext(),hashSet!!.toList())
        binding!!.recyclerViewCrypto.adapter = adapter
    }

//    override fun onQueryTextSubmit(query: String?): Boolean {
//        return true
//    }
//
//    override fun onQueryTextChange(query: String?): Boolean {
//        if(query != null){
//            searchDatabase(query)
//        }
//        return true
//    }

    private fun searchDatabase(query: String){
        val searchQuery = "%$query%"

        cryptoViewModel.searchDatabase(searchQuery).observe(this, {list ->
               adapter.setData(list)
               adapter.notifyDataSetChanged()
        })
    }
}