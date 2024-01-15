package com.reactright.reactright.di

import com.reactright.reactright.data.repo.ItemRepository
import com.reactright.reactright.data.repo.ItemRepositoryInterface
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModel {

    @Binds
    abstract fun bindItemRepository(nrp : ItemRepository) : ItemRepositoryInterface


}