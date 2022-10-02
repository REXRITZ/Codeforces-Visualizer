package com.ritesh.codeforcesvisualizer.di

import android.content.Context
import androidx.room.Room
import com.ritesh.codeforcesvisualizer.data.local.AppDatabase
import com.ritesh.codeforcesvisualizer.data.remote.CodeforcesApi
import com.ritesh.codeforcesvisualizer.repository.ContestRepository
import com.ritesh.codeforcesvisualizer.repository.StatsRepository
import com.ritesh.codeforcesvisualizer.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideRetrofitInstance(): CodeforcesApi {
        return Retrofit.Builder()
            .baseUrl(CodeforcesApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CodeforcesApi::class.java)
    }

    @Provides
    @Singleton
    fun provideContestRepository(
        api: CodeforcesApi,
        appDatabase: AppDatabase
    ): ContestRepository {
        return ContestRepository(api,appDatabase.getContestDao())
    }

    @Provides
    @Singleton
    fun provideStatsRepository(
        api: CodeforcesApi,
        appDatabase: AppDatabase
    ): StatsRepository {
        return StatsRepository(api, appDatabase.getUserDao())
    }

    @Provides
    @Singleton
    fun provideUserRepository(
        api: CodeforcesApi,
        appDatabase: AppDatabase
    ): UserRepository = UserRepository(api,appDatabase.getUserDao())

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        ).build()
    }
}