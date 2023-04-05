package com.petrs.smartlab.ui.fragments.main.profile

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.petrs.smartlab.R
import com.petrs.smartlab.databinding.FragmentProfileBinding
import com.petrs.smartlab.ui.base.BaseFragment
import com.petrs.smartlab.utils.getRealPathFromURI
import com.petrs.smartlab.utils.network.State
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : BaseFragment<FragmentProfileBinding, ProfileViewModel>(
    FragmentProfileBinding::inflate
) {
    override val viewModel: ProfileViewModel by viewModel()

    private val contentUri: Uri? = null

    private val permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
        if (it) photoLauncher.launch(getContentIntent())
    }

    private var photoLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
        if (activityResult.data != null) {
            viewModel.updateProfilePhoto(getRealPathFromURI(requireContext(), activityResult.data!!.data!!)!!)
        }
    }

    override fun initView() {
        viewModel.getProfile()
        binding.apply {
            cardView.background = ContextCompat.getDrawable(requireContext(), R.drawable.profile_photo_background)
            cardView.setOnClickListener {
                if (checkReadStoragePermission()) {
                    photoLauncher.launch(getContentIntent())
                } else {
                    permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                }
            }
            btnSave.setOnClickListener {
                viewModel.updateProfile(
                    name = etName.text.toString(),
                    lastName = etLastname.text.toString(),
                    midName = etMidname.text.toString(),
                    birthDate = etBirthDate.text.toString(),
                    sexOrientation = spinnerSexOrientation.selectedItem.toString()
                )
            }
        }
    }

    override fun observeViewModel() {
        viewModel.apply {
            profile.observe { state ->
                when (state) {
                    is State.Success -> {
                        if (state.data.isNotEmpty()) {
                            binding.apply {
                                Glide.with(requireContext())
                                    .load(state.data[0].image)
                                    .error(R.drawable.ic_camera)
                                    .into(ivProfilePhoto)

                                etName.setText(state.data[0].firstName)
                                etLastname.setText(state.data[0].lastName)
                                etMidname.setText(state.data[0].midName)
                                etBirthDate.setText(state.data[0].birth)

                                if (spinnerSexOrientation.selectedItem.toString() != state.data[0].sexOrientation) {
                                    spinnerSexOrientation.setSelection(1)
                                }

                                saveProfile(state.data)
                            }
                        } else {
                            findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToCreateClientCardFragment2())
                        }
                    }
                    is State.Error -> {}
                    State.Loading -> {}
                }
            }
        }
    }

    private fun checkReadStoragePermission() = ContextCompat.checkSelfPermission(
        requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE
    ) == PackageManager.PERMISSION_GRANTED

    private fun getContentIntent() = Intent()
        .setType("*/*")
        .putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("image/jpg", "image/png", "image/jpeg", "video/mov", "video/mp4"))
        .setAction(Intent.ACTION_GET_CONTENT)

}