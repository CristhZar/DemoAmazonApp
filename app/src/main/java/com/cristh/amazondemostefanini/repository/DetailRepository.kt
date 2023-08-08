package com.cristh.amazondemostefanini.repository

import android.content.Context
import com.cristh.amazondemostefanini.model.AppDetails
import com.cristh.amazondemostefanini.model.AppDetailsX
import com.cristh.amazondemostefanini.util.readRawResource
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class DetailRepository @Inject constructor(
    @ApplicationContext val context: Context
) {

    suspend fun getDetailsFromJsonResource(resource: Int): AppDetailsX {
        val jsonString = context.readRawResource( resource )

        val details = Gson().fromJson(jsonString, AppDetails::class.java)

        return details.app_details
    }

}