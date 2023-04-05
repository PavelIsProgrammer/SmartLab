package com.petrs.smartlab.ui.fragments.start.onboarding

import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.petrs.smartlab.R
import com.petrs.smartlab.databinding.FragmentOnboardingBinding
import com.petrs.smartlab.ui.base.BaseFragment
import com.petrs.smartlab.ui.fragments.start.onboarding.pager.OnboardingPagerAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class OnboardingFragment : BaseFragment<FragmentOnboardingBinding, OnboardingViewModel>(
    FragmentOnboardingBinding::inflate
) {

    override val viewModel: OnboardingViewModel by viewModel()

    override fun initView() {
        binding.apply {
            vpOnboarding.adapter = OnboardingPagerAdapter(this@OnboardingFragment, viewModel.getOnboardingData())

            TabLayoutMediator(
                tlOnboardingScreens,
                vpOnboarding,
                true
            ) { _, _ -> }.attach()

            btnSkip.setOnClickListener {
                findNavController().navigate(OnboardingFragmentDirections.actionOnboardingFragmentToSignInFragment())
            }

            vpOnboarding.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    viewModel.changePage(position)
                }
            })
        }
    }

    override fun observeViewModel() {
        viewModel.apply {
            isEnd.observe { isEnd ->
                if (isEnd) {
                    binding.btnSkip.setText(R.string.text_finish)
                } else {
                    binding.btnSkip.setText(R.string.text_skip)
                }
            }
        }
    }
}