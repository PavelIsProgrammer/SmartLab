package com.petrs.smartlab.di

import com.petrs.smartlab.ui.activities.main.MainActivityViewModel
import com.petrs.smartlab.ui.activities.login.LoginViewModel
import com.petrs.smartlab.ui.fragments.main.analyzes.AnalyzesViewModel
import com.petrs.smartlab.ui.fragments.main.cart.CartViewModel
import com.petrs.smartlab.ui.fragments.main.order_payed.OrderPayedViewModel
import com.petrs.smartlab.ui.fragments.main.order_register.OrderRegisterViewModel
import com.petrs.smartlab.ui.fragments.main.payment.PaymentViewModel
import com.petrs.smartlab.ui.fragments.main.profile.ProfileViewModel
import com.petrs.smartlab.ui.fragments.main.results.ResultsViewModel
import com.petrs.smartlab.ui.fragments.main.support.SupportViewModel
import com.petrs.smartlab.ui.fragments.start.client_card.CreateClientCardViewModel
import com.petrs.smartlab.ui.fragments.start.create_password.CreatePasswordViewModel
import com.petrs.smartlab.ui.fragments.start.email_code.EmailCodeViewModel
import com.petrs.smartlab.ui.fragments.start.onboarding.OnboardingViewModel
import com.petrs.smartlab.ui.fragments.start.sign_in.SignInViewModel
import com.petrs.smartlab.ui.fragments.start.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val uiModule = module {

    viewModel {
        LoginViewModel()
    }

    viewModel {
        SplashViewModel(
            getAppPasswordUseCase = get(),
            getTokenUseCase = get()
        )
    }

    viewModel {
        OnboardingViewModel()
    }

    viewModel {
        SignInViewModel(
            sendCode = get()
        )
    }

    viewModel {
        EmailCodeViewModel(
            sendCodeUseCase = get(),
            signInUseCase = get(),
            saveTokenUseCase = get()
        )
    }

    viewModel {
        CreatePasswordViewModel(
            getProfileUseCase = get(),
            savePasswordUseCase = get()
        )
    }

    viewModel {
        CreateClientCardViewModel(
            createProfileUseCase = get(),
            saveProfileUseCase = get()
        )
    }

    viewModel {
        MainActivityViewModel()
    }

    viewModel {
        AnalyzesViewModel(
            getCatalogUseCase = get(),
            getNewsUseCase = get(),
            getCartUseCase = get(),
            saveCartUseCase = get()
        )
    }

    viewModel {
        CartViewModel(
            getCartUseCase = get(),
            saveCartUseCase = get()
        )
    }

    viewModel {
        ResultsViewModel()
    }

    viewModel {
        SupportViewModel()
    }

    viewModel {
        ProfileViewModel(
            getProfileUseCase = get(),
            updateProfileUseCase = get(),
            saveProfileUseCase = get(),
            updateProfilePhotoUseCase = get()
        )
    }

    viewModel {
        OrderRegisterViewModel(
            getCartUseCase = get(),
            saveProfileUseCase = get(),
            createProfileUseCase = get(),
            getProfileUseCase = get(),
            createOrderUseCase = get(),
            saveCartUseCase = get()
        )
    }

    viewModel {
        PaymentViewModel()
    }

    viewModel {
        OrderPayedViewModel()
    }
}