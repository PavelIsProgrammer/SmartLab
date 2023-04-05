package com.petrs.smartlab.ui.fragments.start.sign_in

import androidx.core.widget.doAfterTextChanged
import androidx.navigation.fragment.findNavController
import com.petrs.smartlab.R
import com.petrs.smartlab.data.ErrorType
import com.petrs.smartlab.databinding.FragmentSignInBinding
import com.petrs.smartlab.domain.DomainResult
import com.petrs.smartlab.domain.LoadingState
import com.petrs.smartlab.ui.activities.login.LoginActivity
import com.petrs.smartlab.ui.base.BaseFragment
import com.petrs.smartlab.ui.base.error_dialog.ErrorDialog
import com.petrs.smartlab.ui.base.error_dialog.ErrorDialogParams
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignInFragment : BaseFragment<FragmentSignInBinding, SignInViewModel>(
    FragmentSignInBinding::inflate
) {

    override val viewModel: SignInViewModel by viewModel()

    override fun initView() {
        binding.apply {
            etEmail.doAfterTextChanged {
                viewModel.validateEmail(it.toString())
            }

            btnNext.setOnClickListener {
                viewModel.sendNewCode(etEmail.text.toString())
            }
        }
    }

    override fun observeViewModel() {
        viewModel.apply {
            emailIsValid.observe { isValid ->
                if (isValid) {
                    binding.btnNext.setBackgroundResource(R.drawable.filled_btn)
                    binding.btnNext.isEnabled = true
                } else {
                    binding.btnNext.setBackgroundResource(R.drawable.blocked_btn)
                    binding.btnNext.isEnabled = false
                }
            }

            sendCodeToEmail.observe { domainResult ->
                when (domainResult) {
                    is DomainResult.Success -> {
                        findNavController().navigate(
                            SignInFragmentDirections.actionSignInFragmentToEmailCodeFragment(
                                binding.etEmail.text.toString()
                            )
                        )
                        (requireActivity() as LoginActivity).hideLoading()
                    }
                    is DomainResult.Error -> {
                        val message = if (domainResult.type == ErrorType.NO_INTERNET)
                            getString(R.string.network_error)
                        else
                            domainResult.errors
                        val errorDialog =
                            ErrorDialog.getInstance(ErrorDialogParams(message))
                        errorDialog.show(childFragmentManager, ErrorDialog.TAG)
                    }
                    is DomainResult.Loading -> {
                        if (domainResult.state == LoadingState.REQUEST_LOADING) (requireActivity() as LoginActivity).showLoading()
                    }
                }
            }
        }
    }
}