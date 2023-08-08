package com.cristh.amazondemostefanini.data

/**
 * Copyright Cristhian Lopez Ramirez
 * This demo is intended for educational and professional assessment purposes
 * General purpose class that holds values from API calls, DB queries or other data source result
 * */

class DataOrException<T, Boolean, E: Exception> (
    var data: T? = null,
    var loading: kotlin.Boolean? = null,
    var e: E? = null
)