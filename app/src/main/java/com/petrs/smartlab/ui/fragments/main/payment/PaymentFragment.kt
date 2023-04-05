package com.petrs.smartlab.ui.fragments.main.payment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.petrs.smartlab.R
import com.petrs.smartlab.databinding.FragmentPaymentBinding
import com.petrs.smartlab.ui.base.BaseFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class PaymentFragment : BaseFragment<FragmentPaymentBinding, PaymentViewModel>(
    FragmentPaymentBinding::inflate
) {
    override val viewModel: PaymentViewModel by viewModel()

    override fun initView() {
        binding.apply {
            lifecycleScope.launch {
                for (i in 0..3) {
                    viewModel.start(i)
                    delay(700)
                }
            }
        }
    }

    override fun observeViewModel() {
        viewModel.apply {
            status.observe {
                when (it) {
                    1 -> {
                        binding.tvText.text = getString(R.string.text_connect_bank)
                    }
                    2 -> {
                        binding.tvText.text = getString(R.string.text_confirm_payment)
                    }
                    3 -> {
                        findNavController().navigate(PaymentFragmentDirections.actionPaymentFragmentToOrderPayedFragment())
                    }
                }
            }
        }
    }
}