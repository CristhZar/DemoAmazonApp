package com.cristh.amazondemostefanini.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cristh.amazondemostefanini.model.AppItem
import com.cristh.amazondemostefanini.repository.AppsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Copyright Cristhian Lopez Ramirez
 * This demo is intended for educational and professional assessment purposes
 * Viewmodel for Main activity (main screen)
 * TODO -> Add repository for API calls
 * */

@HiltViewModel
class MainViewModel @Inject constructor(
    val appsRepository: AppsRepository
): ViewModel() {
    var mutableList: MutableList<AppItem> = mutableListOf()
    var categoryList: ArrayList<String> = ArrayList()

    init {
        viewModelScope.launch {
            getDataFromRawJson()
        }
    }

    suspend fun getDataFromRawJson() {
        mutableList = appsRepository.getAppsFromStoreJson()
        if (mutableList.size > 0) {
            initCategories()
        }
    }

    private fun initCategories() {
        for (item in mutableList) {
            for (cat in item.categories) {
                if ( !categoryList.contains(cat) ) {
                    categoryList.add(cat)
                }
            }
        }
        Log.i("MainViewModel", "Category list -> $categoryList")
    }

    fun filterByCategory(selectedCategory: String): List<AppItem> {
        var result = mutableListOf<AppItem>()
        for (app in mutableList) {
            if (app.categories.contains(selectedCategory)) {
                result.add(app)
            }
        }
        return result
    }
}