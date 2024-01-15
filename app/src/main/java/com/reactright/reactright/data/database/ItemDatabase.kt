package com.reactright.reactright.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.reactright.reactright.data.dao.ItemDao
import com.reactright.reactright.data.entity.Items

@Database(entities = [Items::class], version = 1)
abstract class ItemDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDao
}