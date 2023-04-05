package com.petrs.smartlab.ui.fragments.start.client_card

import android.content.Intent
import android.util.Log
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.fragment.findNavController
import com.petrs.smartlab.R
import com.petrs.smartlab.data.ErrorType
import com.petrs.smartlab.databinding.FragmentCreateClientCardBinding
import com.petrs.smartlab.domain.DomainResult
import com.petrs.smartlab.domain.LoadingState
import com.petrs.smartlab.ui.activities.main.MainActivity
import com.petrs.smartlab.ui.base.BaseFragment
import com.petrs.smartlab.ui.base.error_dialog.ErrorDialog
import com.petrs.smartlab.ui.base.error_dialog.ErrorDialogParams
import com.petrs.smartlab.ui.base.loading_dialog.LoadingDialog
import com.petrs.smartlab.utils.network.State
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreateClientCardFragment :
    BaseFragment<FragmentCreateClientCardBinding, CreateClientCardViewModel>(
        FragmentCreateClientCardBinding::inflate
    ) {

    override val viewModel: CreateClientCardViewModel by viewModel()

    override fun initView() {
        binding.apply {
            btnSkip.isVisible = findNavController().backQueue[findNavController().backQueue.lastIndex - 1].destination.id == R.id.createPasswordFragment

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
                viewModel.createProfile(
                    name = etName.text.toString(),
                    lastName = etLastname.text.toString(),
                    midName = etMidname.text.toString(),
                    birthDate = etBirthDate.text.toString(),
                    sexOrientation = spinnerSexOrientation.selectedItem.toString()
                )
            }

            btnSkip.setOnClickListener {
                startMainActivity()
            }
        }
    }

    override fun observeViewModel() {
        viewModel.apply {
            val loadingDialog = LoadingDialog.getInstance()
            profileInfo.observe { state ->
                when (state) {
                    is DomainResult.Success -> {
                        saveProfile(state.data)
                        loadingDialog.dismiss()
                    }
                    is DomainResult.Error -> {
                        val message = if (state.type == ErrorType.NO_INTERNET)
                            getString(R.string.network_error)
                        else
                            state.errors
                        val errorDialog =
                            ErrorDialog.getInstance(ErrorDialogParams(message))
                        errorDialog.show(childFragmentManager, ErrorDialog.TAG)
                    }
                    is DomainResult.Loading -> {
                        if (state.state == LoadingState.REQUEST_LOADING)
                        loadingDialog.show(childFragmentManager, LoadingDialog.TAG)
                    }
                }
            }
            saveProfile.observe { state ->
                when (state) {
                    is State.Success -> {
                        startMainActivity()
                    }
                    is State.Error -> {}
                    is State.Loading -> {}
                }
            }
        }
    }

    private fun startMainActivity() {
        val i = Intent(requireContext(), MainActivity::class.java)
        startActivity(i)
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
}