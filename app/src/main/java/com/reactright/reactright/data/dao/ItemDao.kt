package com.reactright.reactright.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.reactright.reactright.data.entity.Items

@Dao
interface ItemDao {

    @Query("SELECT * FROM items")
    fun getAll() : LiveData<List<Items>>

}