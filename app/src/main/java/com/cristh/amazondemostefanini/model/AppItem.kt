package com.cristh.amazondemostefanini.model


data class AppItem(
    val categories: List<String>,
    val description: String,
    val developer: String,
    val icon: String,
    val id: String,
    val name: String,
    val price: Double,
    val rating: Double
)