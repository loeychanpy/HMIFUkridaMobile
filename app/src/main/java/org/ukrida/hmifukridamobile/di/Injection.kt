package org.ukrida.hmifukridamobile.di

import org.ukrida.hmifukridamobile.data.api.RetrofitInstance
import org.ukrida.hmifukridamobile.data.repository.EventRepository
import org.ukrida.hmifukridamobile.data.repository.UserRepository

object Injection {
    fun provideUserRepository(): UserRepository =
        UserRepository(RetrofitInstance.api)

    fun provideEventRepository(): EventRepository =
        EventRepository(RetrofitInstance.api)
}