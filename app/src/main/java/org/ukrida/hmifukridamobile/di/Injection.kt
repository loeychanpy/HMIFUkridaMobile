package org.ukrida.hmifukridamobile.di

import android.content.Context
import org.ukrida.hmifukridamobile.data.api.RetrofitInstance
import org.ukrida.hmifukridamobile.data.local.TokenManager
import org.ukrida.hmifukridamobile.data.repository.EventRepository
import org.ukrida.hmifukridamobile.data.repository.UserRepository

object Injection {
    fun provideUserRepository(): UserRepository =
        UserRepository(RetrofitInstance.api)

    fun provideEventRepository(): EventRepository =
        EventRepository(RetrofitInstance.api)

    fun provideTokenManager(context: Context): TokenManager =
        TokenManager(context.applicationContext)
}
