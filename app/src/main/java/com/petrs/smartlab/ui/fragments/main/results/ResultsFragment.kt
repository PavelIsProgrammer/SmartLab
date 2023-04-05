package com.petrs.smartlab.ui.fragments.main.results

import com.petrs.smartlab.databinding.FragmentResultsBinding
import com.petrs.smartlab.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class ResultsFragment : BaseFragment<FragmentResultsBinding, ResultsViewModel>(
    FragmentResultsBinding::inflate
) {
    override val viewModel: ResultsViewModel by viewModel()

    override fun initView() {
        binding.apply {

        }
    }

    override fun observeViewModel() {
        binding.apply {

        }
    }


}