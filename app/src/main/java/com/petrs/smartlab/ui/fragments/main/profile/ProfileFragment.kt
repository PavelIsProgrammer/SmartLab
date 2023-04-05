package com.petrs.smartlab.ui.fragments.main.profile

import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.petrs.smartlab.R
import com.petrs.smartlab.databinding.FragmentProfileBinding
import com.petrs.smartlab.ui.base.BaseFragment
import com.petrs.smartlab.utils.network.State
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.nio.file.DirectoryIteratorException

class ProfileFragment : BaseFragment<FragmentProfileBinding, ProfileViewModel>(
    FragmentProfileBinding::inflate
) {
    override val viewModel: ProfileViewModel by viewModel()

    override fun initView() {
        viewModel.getProfile()
        binding.apply {
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

}