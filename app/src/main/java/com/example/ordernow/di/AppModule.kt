package com.example.ordernow.di

import com.example.ordernow.data.repository.HomeRepositoryImpl
import com.example.ordernow.domain.repository.HomeRepository
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideHomeRepository(
        databaseRealTime: FirebaseDatabase
    ): HomeRepository {
        return HomeRepositoryImpl(
            databaseRealTime = databaseRealTime
        )
    }

}