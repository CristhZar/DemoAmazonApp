package com.cristh.amazondemostefanini.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cristh.amazondemostefanini.model.AppDetailsX
import com.cristh.amazondemostefanini.repository.DetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Copyright Cristhian Lopez Ramirez
 * This demo is intended for educational and professional assessment purposes
 * Viewmodel for detail fragment
 * */

@HiltViewModel
class DetailViewModel @Inject constructor(
    val detailRepository: DetailRepository
): ViewModel() {
    var details: AppDetailsX? = null

    fun getDetailsFromJson(resource: Int) {
        viewModelScope.launch {
            details = detailRepository.getDetailsFromJsonResource(resource)
        }
    }

}