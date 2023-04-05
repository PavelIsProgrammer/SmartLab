package com.petrs.smartlab.ui.base.error_dialog

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.petrs.smartlab.R
import com.petrs.smartlab.databinding.FragmentErrorDialogBinding
import kotlinx.parcelize.Parcelize

@Parcelize
data class ErrorDialogParams(
    val message: String
): Parcelable

class ErrorDialog : DialogFragment() {

    private lateinit var binding: FragmentErrorDialogBinding

    private val params by lazy {
        checkNotNull(requireArguments().getParcelable<ErrorDialogParams>(PARAMS))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(requireContext(), R.color.transparent)))
        binding = FragmentErrorDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            tvError.text = params.message

            btnClose.setOnClickListener {
                dismiss()
            }
        }
    }

    companion object {
        val TAG = "${ErrorDialog::class.simpleName}_TAG"
        const val PARAMS = "PARAMS"

        fun getInstance(params: ErrorDialogParams) = ErrorDialog().apply {
            arguments = bundleOf(PARAMS to params)
        }
    }
}