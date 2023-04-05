package com.petrs.smartlab.ui.fragments.start.onboarding.pager

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.petrs.smartlab.databinding.FragmentOnboardingPagerBinding
import kotlinx.parcelize.Parcelize

@Parcelize
data class OnboardingPagerFragmentParams(
    val title: String,
    val description: String,
    @DrawableRes val image: Int
) : Parcelable

class OnboardingPagerFragment : Fragment() {

    private lateinit var binding: FragmentOnboardingPagerBinding

    private val params by lazy {
        checkNotNull(requireArguments().getParcelable<OnboardingPagerFragmentParams>(PARAMS))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnboardingPagerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            tvTitle.text = params.title
            tvDescription.text = params.description

            Glide.with(requireContext())
                .load(params.image)
                .into(ivImage)
        }
    }

    companion object {
        const val PARAMS = "PARAMS"

        fun getInstance(params: OnboardingPagerFragmentParams) = OnboardingPagerFragment().apply {
            arguments = bundleOf(PARAMS to params)
        }
    }
}