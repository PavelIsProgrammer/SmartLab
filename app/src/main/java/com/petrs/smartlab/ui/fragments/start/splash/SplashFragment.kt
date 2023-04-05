package com.petrs.smartlab.ui.fragments.start.splash

import android.content.Intent
import androidx.navigation.fragment.findNavController
import com.petrs.smartlab.databinding.FragmentSplashBinding
import com.petrs.smartlab.ui.activities.main.MainActivity
import com.petrs.smartlab.ui.base.BaseFragment
import com.petrs.smartlab.utils.network.State
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashFragment : BaseFragment<FragmentSplashBinding, SplashViewModel>(
    FragmentSplashBinding::inflate
) {

    override val viewModel: SplashViewModel by viewModel()

    override fun initView() {
        viewModel.checkToken()
    }

    override fun observeViewModel() {
        viewModel.apply {
//            timer.observe { goNext ->
//                if (goNext) {
//                    findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToOnboardingFragment())
//                }
//            }
            token.observe { state ->
                when (state) {
                    is State.Success -> {
                        if (state.data.isNotEmpty()) {
                            checkPassword()
                        } else {
                            findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToOnboardingFragment())
                        }
                    }
                    is State.Error -> {}
                    is State.Loading -> {}
                }
            }

            password.observe { state ->
                when (state) {
                    is State.Success -> {
                        if (state.data.isNotEmpty()) {
                            startMainActivity()
                        } else {
                            findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToCreatePasswordFragment())
                        }
                    }
                    is State.Error -> {}
                    is State.Loading -> {}
                }
            }
        }
    }

    private fun startMainActivity() {
        val i = Intent(requireContext(), MainActivity::class.java)
        startActivity(i)
    }
}