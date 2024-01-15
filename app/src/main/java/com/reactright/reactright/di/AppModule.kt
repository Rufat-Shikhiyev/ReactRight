package com.reactright.reactright.di

import android.content.Context
import androidx.room.Room
import com.reactright.reactright.data.dao.ItemDao
import com.reactright.reactright.data.database.ItemDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideItemDatabase(@ApplicationContext context: Context): ItemDatabase {
        return Room.databaseBuilder(context, ItemDatabase::class.java, "items.sqlite")
            .createFromAsset("items.sqlite").build()
    }


    @Provides
    @Singleton
    fun provideItemDao(itemDatabase: ItemDatabase): ItemDao {
        return itemDatabase.itemDao()
    }

}