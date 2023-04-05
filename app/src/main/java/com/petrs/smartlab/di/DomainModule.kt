package com.petrs.smartlab.di

import com.petrs.smartlab.domain.useCases.*
import org.koin.dsl.module

val domainModule = module {
    factory {
        SendCodeUseCase(
            repository = get()
        )
    }

    factory {
        SignInUseCase(
            repository = get()
        )
    }

    factory {
        CreateProfileUseCase(
            sharedPreferencesRepository = get(),
            repository = get()
        )
    }

    factory {
        GetTokenUseCase(
            sharedPreferencesRepository = get()
        )
    }

    factory {
        SaveTokenUseCase(
            sharedPreferencesRepository = get()
        )
    }

    factory {
        SaveAppPasswordUseCase(
            sharedPreferencesRepository = get()
        )
    }

    factory {
        GetAppPasswordUseCase(
            sharedPreferencesRepository = get()
        )
    }

    factory {
        GetCatalogUseCase(
            repository = get()
        )
    }

    factory {
        GetNewsUseCase(
            repository = get()
        )
    }

    factory {
        SaveProfileUseCase(
            sharedPreferencesRepository = get()
        )
    }

    factory {
        GetProfileUseCase(
            sharedPreferencesRepository = get()
        )
    }

    factory {
        SaveCartUseCase(
            sharedPreferencesRepository = get()
        )
    }

    factory {
        GetCartUseCase(
            sharedPreferencesRepository = get()
        )
    }

    factory {
        UpdateProfileUseCase(
            repository = get(),
            sharedPreferencesRepository = get()
        )
    }

    factory {
        SaveAddressesUseCase(
            sharedPreferencesRepository = get()
        )
    }

    factory {
        GetAddressesUseCase(
            sharedPreferencesRepository = get()
        )
    }

    factory {
        CreateOrderUseCase(
            repository = get(),
            sharedPreferencesRepository = get()
        )
    }

    factory {
        UpdateProfilePhotoUseCase(
            repository = get(),
            sharedPreferencesRepository = get()
        )
    }
}