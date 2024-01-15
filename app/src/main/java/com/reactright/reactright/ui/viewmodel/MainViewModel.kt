package com.reactright.reactright.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.reactright.reactright.data.entity.Items
import com.reactright.reactright.data.repo.ItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor (var repository: ItemRepository): ViewModel() {

    fun getAll(): LiveData<List<Items>> {
        return repository.getAll()
    }

}