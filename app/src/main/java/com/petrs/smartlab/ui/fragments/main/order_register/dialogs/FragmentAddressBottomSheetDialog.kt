package com.petrs.smartlab.ui.fragments.main.order_register.dialogs

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.LocationManager
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.petrs.smartlab.R
import com.petrs.smartlab.databinding.FragmentAddressBottomSheetDialogBinding
import kotlinx.parcelize.Parcelize
import java.util.*


@Parcelize
data class AddressBottomSheetDialogParams(
    val onConfirm: (String, Boolean, String?) -> Unit
) : Parcelable

class FragmentAddressBottomSheetDialog : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentAddressBottomSheetDialogBinding
    private lateinit var permissionLauncher: ActivityResultLauncher<String>

    private val params by lazy {
        checkNotNull(requireArguments().getParcelable<AddressBottomSheetDialogParams>(PARAMS))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddressBottomSheetDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        permissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) {
                if (it) {
                    initGeolocation()
                } else {
                    dismiss()
                }
            }

        initGeolocation()

        binding.apply {
            btnClose.setOnClickListener {
                dismiss()
            }

            switchSaveAddress.setOnCheckedChangeListener { _, b ->
                etAddressTitle.isVisible = b
                checkBlankOrNull()
            }

            btnConfirm.setOnClickListener {
                params.onConfirm(
                    formatAddress(), switchSaveAddress.isChecked,
                    etAddressTitle.text.takeIf { !it.isNullOrBlank() }?.toString()
                )
                dismiss()
            }

            etAddress.doAfterTextChanged {
                checkBlankOrNull()
            }

            etRoom.doAfterTextChanged {
                checkBlankOrNull()
            }

            etAddressTitle.doAfterTextChanged {
                checkBlankOrNull()
            }
        }
    }

    private fun checkBlankOrNull() {
        binding.apply {
            if (!etAddress.text.isNullOrBlank() && !etRoom.text.isNullOrBlank()) {
                if ((switchSaveAddress.isChecked && !etAddressTitle.text.isNullOrBlank()) || !switchSaveAddress.isChecked) {
                    btnConfirm.isEnabled = true
                    btnConfirm.background =
                        ContextCompat.getDrawable(requireContext(), R.drawable.filled_btn)
                } else {
                    btnConfirm.isEnabled = false
                    btnConfirm.background =
                        ContextCompat.getDrawable(requireContext(), R.drawable.blocked_btn)
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun initGeolocation() {
        binding.apply {
            val lm = requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
            if (checkPermissions()) {
                val location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                val longitude = location?.longitude ?: 0.0
                val latitude = location?.latitude ?: 0.0
                val altitude = location?.altitude ?: 0.0

                etLongitude.setText(longitude.toString())
                etLatitude.setText(latitude.toString())
                etAltitude.setText(altitude.toString())

                val geocoder = Geocoder(requireContext(), Locale.getDefault())
                val addresses = geocoder.getFromLocation(latitude, longitude, 1)

                if (addresses.isNotEmpty()) {
                    etAddress.setText(addresses[0].featureName)
                } else {
                    Toast.makeText(requireContext(), "Извините, мы не смогли автоматически определить ваше местоположение", Toast.LENGTH_SHORT).show()
                }
            } else {
                permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }

    private fun checkPermissions() = ContextCompat.checkSelfPermission(
        requireContext(),
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

    private fun formatAddress() =
        "${binding.etAddress.text}, кв. ${binding.etRoom.text}" +
                (if (!binding.etEntrance.text.isNullOrBlank()) ", подъезд ${binding.etEntrance.text}" else "") +
                (if (!binding.etFloor.text.isNullOrBlank()) ", этаж ${binding.etFloor.text}" else "") +
                if (!binding.etIntercom.text.isNullOrBlank()) ", домофон ${binding.etIntercom.text}" else ""

    companion object {
        const val PARAMS = "PARAMS"
        val TAG = "${FragmentAddressBottomSheetDialog::class.simpleName}_TAG"

        fun getInstance(params: AddressBottomSheetDialogParams) =
            FragmentAddressBottomSheetDialog().apply {
                arguments = bundleOf(PARAMS to params)
            }
    }
}