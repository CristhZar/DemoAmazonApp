package com.cristh.amazondemostefanini.screen.main

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.SearchView
import android.widget.SearchView.OnCloseListener
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.cristh.amazondemostefanini.R
import com.cristh.amazondemostefanini.databinding.ActivityMainBinding
import com.cristh.amazondemostefanini.model.AppItem
import com.cristh.amazondemostefanini.ui.AppItemGVAdapter
import com.cristh.amazondemostefanini.util.amazonLogoUrl
import com.cristh.amazondemostefanini.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint


/**
 * Copyright Cristhian Lopez Ramirez
 * This demo is intended for educational and professional assessment purposes
 * Dependency injection module
 * -> TODO set API dependency (i.e. Retrofit) as part of future work
 * */

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var searchListAdapter: ArrayAdapter<String>
    private lateinit var gridAppsAdapter: AppItemGVAdapter
    private var binding: ActivityMainBinding? = null

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("MainActivity", "${mainViewModel.mutableList}")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        // region adapters setup

        searchListAdapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_list_item_1,
            mainViewModel.categoryList
        )

        gridAppsAdapter = AppItemGVAdapter(this, mainViewModel.mutableList)

        // endregion

        binding?.let {
            // region app bar setup
            it.mainAppBar.appLogo.load(amazonLogoUrl) {
                placeholder(R.drawable.splash_icon)
            }
            // endregion

            // region search setup

            it.categorySearchList.setOnItemClickListener { parent,  view, position, id ->
                it.appSearchView.setQuery(
                    searchListAdapter.getItem(position),
                     true
                )
            }

            it.categorySearchList.adapter = searchListAdapter

            it.appSearchView.setOnClickListener { view ->
                it.categorySearchList.visibility = View.VISIBLE
            }
            it.appSearchView.setOnCloseListener {
                Log.i("MainActivity", "Search view closed")
                false
            }

            it.appSearchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (mainViewModel.categoryList.contains(query)) {
                        searchListAdapter.filter.filter(query)
                        it.categorySearchList.visibility = View.GONE
                        // update gridview
                        if(query.isNullOrBlank()) {
                            gridAppsAdapter.itemList = mainViewModel.mutableList
                        } else {
                            gridAppsAdapter.itemList = mainViewModel.filterByCategory(query)
                        }
                        gridAppsAdapter.notifyDataSetInvalidated()
                    } else {
                        it.noResultsText.visibility = View.VISIBLE
                        it.resultGridView.visibility = View.GONE
                    }
                    return false
                }

                override fun onQueryTextChange(newString: String?): Boolean {
                    if (newString.isNullOrBlank()){
                        it.categorySearchList.visibility = View.GONE
                    } else {
                        it.categorySearchList.visibility = View.VISIBLE
                    }

                    it.noResultsText.visibility = View.GONE
                    searchListAdapter.filter.filter(newString)
                    return false
                }

            })

            // endregion

            // region grid layout setup

            // TODO ->Change this to fit design
            //it.resultGridView.numColumns= 3
            it.resultGridView.adapter = gridAppsAdapter
            it.resultGridView.onItemClickListener =
                OnItemClickListener { parent, view, position, id ->
                    (parent.adapter as AppItemGVAdapter).itemList?.let {
                        showDetailsFragment(
                            it.get(position)
                        )
                    } ?: Toast.makeText(
                        applicationContext,
                        "Error, please try again",
                        Toast.LENGTH_LONG
                    ).show()
                }
            // end region
        }

        setLoadingState(mainViewModel.mutableList.size == 0)
    }

    private fun setLoadingState(started: Boolean) {
        binding?.let {
            if (started) {
                it.noResultsText.text = getString(R.string.loading_text)
                it.noResultsText.visibility = View.VISIBLE
                it.mainProgressBar.visibility = View.VISIBLE
            } else {
                it.noResultsText.text = getString(R.string.no_result_text)
                it.noResultsText.visibility = View.GONE
                it.mainProgressBar.visibility = View.GONE
            }

        }
    }

    private fun showDetailsFragment(item: AppItem) {
        Log.i("MainActivity", "Item selected -> ${item.name}")
        val fragment = AppDetailFragment.newInstance(
            id = item.id,
            name = item.name,
            price = item.price.toFloat(),
            rating = item.rating.toFloat(),
            dev = item.developer,
            icon = item.icon
        )
        val fragManager = supportFragmentManager
        fragManager.beginTransaction()
            .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
            .replace(R.id.rootContainer, fragment, "details")
            .addToBackStack(null)
            .commit()
    }


}