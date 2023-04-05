package com.petrs.smartlab

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textview.MaterialTextView
import com.petrs.smartlab.databinding.FragmentSelectPatientsBottomSheetDialogBinding
import com.petrs.smartlab.ui.fragments.main.order_register.models.PatientItem
import kotlinx.parcelize.Parcelize

@Parcelize
data class SelectPatientsBottomSheetDialogParams(
    val patients: List<PatientItem>,
    val onConfirm: (List<PatientItem>) -> Unit,
    val onAddNewPatient: () -> Unit
) : Parcelable

class FragmentSelectPatientsBottomSheetDialog(
) : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentSelectPatientsBottomSheetDialogBinding
    private val newPatients = mutableListOf<PatientItem>()

    private val params by lazy {
        checkNotNull(requireArguments().getParcelable<SelectPatientsBottomSheetDialogParams>(PARAMS))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSelectPatientsBottomSheetDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newPatients.addAll(params.patients)

        binding.apply {
            btnClose.setOnClickListener {
                dismiss()
            }

            btnAddNewPatient.setOnClickListener {
                params.onAddNewPatient()
                dismiss()
            }

            btnConfirm.setOnClickListener {
                params.onConfirm(newPatients)
                dismiss()
            }
        }

        newPatients.forEach { patientItem ->
            val view = layoutInflater.inflate(R.layout.patient_spinner_item, null)
            val text = view.findViewById<MaterialTextView>(R.id.tv_patient)
            val image = view.findViewById<AppCompatImageView>(R.id.iv_sex_orientation)

            text.apply {
                if (patientItem.selected) {
                    background =
                        ContextCompat.getDrawable(requireContext(), R.drawable.filled_btn)
                    setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                }
                text.text = patientItem.profile.lastName + " " + patientItem.profile.firstName

                setOnClickListener {
                    newPatients.map { mapItem ->
                        if (mapItem.profile.id == patientItem.profile.id) mapItem.selected =
                            !mapItem.selected
                    }

                    background =
                        if (patientItem.selected) ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.filled_btn
                        )
                        else ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.edit_text_background
                        )

                    setTextColor(
                        if (patientItem.selected) ContextCompat.getColor(
                            requireContext(),
                            R.color.white
                        )
                        else ContextCompat.getColor(requireContext(), R.color.black)
                    )
                }

                validate()
            }

            Glide.with(requireContext())
                .load(if (patientItem.profile.sexOrientation == "Мужской") R.drawable.male else R.drawable.female)
                .into(image)

            binding.linearPatients.addView(view)
        }
    }

    private fun validate() {
        binding.apply {
            if (newPatients.find { it.selected } != null) {
                btnConfirm.isEnabled = true
                btnConfirm.background = ContextCompat.getDrawable(requireContext(), R.drawable.filled_btn)
            } else {
                btnConfirm.isEnabled = false
                btnConfirm.background = ContextCompat.getDrawable(requireContext(), R.drawable.blocked_btn)
            }
        }
    }

    companion object {
        const val PARAMS = "PARAMS"
        val TAG = "${FragmentSelectPatientsBottomSheetDialog::class.simpleName}_TAG"

        fun getInstance(params: SelectPatientsBottomSheetDialogParams) =
            FragmentSelectPatientsBottomSheetDialog().apply {
                arguments = bundleOf(PARAMS to params)
            }
    }
}