package com.cristh.amazondemostefanini.model

import com.google.gson.annotations.SerializedName

data class AmazonAppList(
    @SerializedName("apps")
    val appItemList: List<AppItem>,
    val message: String
)