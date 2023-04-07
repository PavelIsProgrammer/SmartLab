package com.petrs.smartlab.ui.fragments.main.analyzes

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.petrs.smartlab.R
import com.petrs.smartlab.databinding.FragmentAnalisisBottomSheetDialogBinding
import com.petrs.smartlab.domain.models.CatalogItemDomain
import kotlinx.parcelize.Parcelize

@Parcelize
data class AnalysisBottomSheetDialogParams(
    val analysis: CatalogItemDomain,
    val onBtnAddClick: () -> Unit,
    val onBtnRemoveClick: () -> Unit
) : Parcelable

class FragmentAnalysisBottomSheetDialog : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentAnalisisBottomSheetDialogBinding

    private val params by lazy {
        checkNotNull(requireArguments().getParcelable<AnalysisBottomSheetDialogParams>(PARAMS))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAnalisisBottomSheetDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            tvTitle.text = params.analysis.title
            tvDescription.text = params.analysis.description
            tvPreparation.text = params.analysis.preparation
            tvTime.text = params.analysis.timeResult
            tvBio.text = params.analysis.bio

            if (params.analysis.inCart == 0) {
                initAddBtn()
            } else {
                initRemoveBtn()
            }

            btnClose.setOnClickListener { dismiss() }
        }
    }

    private fun initAddBtn() {
        binding.btnAdd.apply {
            text = getString(R.string.text_add_price, params.analysis.price)
            setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            background = ContextCompat.getDrawable(requireContext(), R.drawable.filled_btn)
            setOnClickListener {
                params.onBtnAddClick()
                initRemoveBtn()
            }
        }
    }

    private fun initRemoveBtn() {
        binding.btnAdd.apply {
            text = getString(R.string.remove_from_cart)
            setTextColor(ContextCompat.getColor(requireContext(), R.color.second_blue))
            background = ContextCompat.getDrawable(requireContext(), R.drawable.outlined_btn)
            setOnClickListener {
                params.onBtnRemoveClick()
                initAddBtn()
            }
        }
    }

    companion object {
        const val PARAMS = "PARAMS"
        val TAG = "${FragmentAnalysisBottomSheetDialog::class.simpleName}_TAG"

        fun getInstance(params: AnalysisBottomSheetDialogParams) =
            FragmentAnalysisBottomSheetDialog().apply {
                arguments = bundleOf(PARAMS to params)
            }
    }
}