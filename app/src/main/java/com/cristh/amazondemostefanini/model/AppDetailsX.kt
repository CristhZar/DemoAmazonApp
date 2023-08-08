package com.cristh.amazondemostefanini.model

data class AppDetailsX(
    val comments: List<Comment>,
    val description_text: String,
    val developer_name: String,
    val installed: Boolean,
    val rating: Double,
    val screenshots: List<String>
)