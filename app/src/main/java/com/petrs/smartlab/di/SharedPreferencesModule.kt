package com.petrs.smartlab.di

import com.petrs.smartlab.data.sharedPreferences.SharedPreferencesHandler
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val sharedPreferencesModule = module {
    single { SharedPreferencesHandler(androidContext().applicationContext) }
}