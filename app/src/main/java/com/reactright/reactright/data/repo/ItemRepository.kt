package com.reactright.reactright.data.repo

import androidx.lifecycle.LiveData
import com.reactright.reactright.data.dao.ItemDao
import com.reactright.reactright.data.entity.Items
import javax.inject.Inject

interface ItemRepositoryInterface {

}

class ItemRepository @Inject constructor(private val itemDao : ItemDao) : ItemRepositoryInterface{

    fun getAll() : LiveData<List<Items>> {
        return itemDao.getAll()
    }

}