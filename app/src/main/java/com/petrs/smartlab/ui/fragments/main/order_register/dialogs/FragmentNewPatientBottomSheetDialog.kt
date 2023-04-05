package com.petrs.smartlab

import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.petrs.smartlab.databinding.FragmentNewPatientBottomSheetDialogBinding
import com.petrs.smartlab.databinding.FragmentSelectPatientsBottomSheetDialogBinding
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewPatientBottomSheetDialogParams(
    val onConfirm: (String, String, String, String, String) -> Unit
): Parcelable

class FragmentNewPatientBottomSheetDialog : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentNewPatientBottomSheetDialogBinding

    private val params by lazy {
        checkNotNull(requireArguments().getParcelable<NewPatientBottomSheetDialogParams>(PARAMS))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewPatientBottomSheetDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            etName.doAfterTextChanged {
                validateInputs()
            }

            etLastname.doAfterTextChanged {
                validateInputs()
            }

            etMidname.doAfterTextChanged {
                validateInputs()
            }

            etBirthDate.doAfterTextChanged {
                validateInputs()
            }

            btnCreate.setOnClickListener {
                params.onConfirm(
                    etName.text.toString(),
                    etLastname.text.toString(),
                    etMidname.text.toString(),
                    etBirthDate.text.toString(),
                    spinnerSexOrientation.selectedItem.toString()
                )
                dismiss()
            }

            btnClose.setOnClickListener {
                dismiss()
            }
        }
    }

    private fun validateInputs() {
        binding.apply {
            if (!etName.text.isNullOrBlank() && !etLastname.text.isNullOrBlank() && !etMidname.text.isNullOrBlank() && !etBirthDate.text.isNullOrBlank()) {
                btnCreate.isEnabled = true
                btnCreate.setBackgroundResource(R.drawable.filled_btn)
            } else {
                btnCreate.isEnabled = false
                btnCreate.setBackgroundResource(R.drawable.blocked_btn)
            }
        }
    }

    companion object {
        val TAG = "${FragmentNewPatientBottomSheetDialog::class.simpleName}_TAG"
        const val PARAMS = "PARAMS"

        fun getInstance(params: NewPatientBottomSheetDialogParams) = FragmentNewPatientBottomSheetDialog().apply {
            arguments = bundleOf(PARAMS to params)
        }
    }
}