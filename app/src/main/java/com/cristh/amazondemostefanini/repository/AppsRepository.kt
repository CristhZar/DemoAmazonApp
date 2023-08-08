package com.cristh.amazondemostefanini.repository

import android.content.Context
import com.cristh.amazondemostefanini.R
import com.cristh.amazondemostefanini.model.AmazonAppList
import com.cristh.amazondemostefanini.model.AppItem
import com.cristh.amazondemostefanini.util.readRawResource
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


/**
 * Copyright Cristhian Lopez Ramirez
 * This demo is intended for educational and professional assessment purposes
 * Data repository
 * -> TODO create method to retrieve data from API
 * */

class AppsRepository @Inject constructor(
    @ApplicationContext val context: Context
) {

    suspend fun getAppsFromStoreJson(): MutableList<AppItem> {
        val jsonString = context.readRawResource( R.raw.app_result )

        val appList = Gson().fromJson(jsonString, AmazonAppList::class.java)

        return appList.appItemList.toMutableList()
    }
}