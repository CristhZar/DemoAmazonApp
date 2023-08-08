package com.cristh.amazondemostefanini.model

data class Comment(
    val comemnt_text: String,
    val id: Int,
    val user_rating: Int,
    val username: String
)