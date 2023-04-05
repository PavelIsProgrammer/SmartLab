package com.petrs.smartlab.ui.fragments.main.support

import com.petrs.smartlab.databinding.FragmentSupportBinding
import com.petrs.smartlab.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class SupportFragment : BaseFragment<FragmentSupportBinding, SupportViewModel>(
    FragmentSupportBinding::inflate
) {
    override val viewModel: SupportViewModel by viewModel()

    override fun initView() {
        binding.apply {

        }
    }

    override fun observeViewModel() {
        viewModel.apply {

        }
    }


}