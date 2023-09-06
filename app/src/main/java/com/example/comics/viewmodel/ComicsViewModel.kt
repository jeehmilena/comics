package com.example.comics.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.comics.model.ItemModel
import com.example.comics.repository.ComicsRepository
import com.example.comics.util.Result
import com.example.comics.util.safeRunDispatcher
import kotlinx.coroutines.launch

class ComicsViewModel(private val repository: ComicsRepository) : ViewModel() {
    private var _result: MutableLiveData<Result<ItemModel>> = MutableLiveData()
    val result: LiveData<Result<ItemModel>> = _result

    fun getListComics(){
        viewModelScope.launch {
            val response = safeRunDispatcher {
                repository.getComics()
            }
            _result.value = response
        }
    }
}