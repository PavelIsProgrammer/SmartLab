package com.petrs.smartlab.di

import com.petrs.smartlab.data.repositoryImpl.SharedPreferencesRepositoryImpl
import com.petrs.smartlab.data.repositoryImpl.SmartLabRepositoryImpl
import com.petrs.smartlab.domain.repository.SharedPreferencesRepository
import com.petrs.smartlab.domain.repository.SmartLabRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<SmartLabRepository> {
        SmartLabRepositoryImpl(
            api = get()
        )
    }

    single<SharedPreferencesRepository> {
        SharedPreferencesRepositoryImpl(
            handler = get()
        )
    }
}