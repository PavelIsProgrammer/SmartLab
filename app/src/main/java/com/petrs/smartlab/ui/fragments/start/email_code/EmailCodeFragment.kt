package com.petrs.smartlab.ui.fragments.start.email_code

import androidx.core.widget.doAfterTextChanged
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.petrs.smartlab.R
import com.petrs.smartlab.data.ErrorType
import com.petrs.smartlab.databinding.FragmentEmailCodeBinding
import com.petrs.smartlab.domain.DomainResult
import com.petrs.smartlab.domain.LoadingState
import com.petrs.smartlab.ui.activities.login.LoginActivity
import com.petrs.smartlab.ui.base.BaseFragment
import com.petrs.smartlab.ui.base.error_dialog.ErrorDialog
import com.petrs.smartlab.ui.base.error_dialog.ErrorDialogParams
import com.petrs.smartlab.ui.base.loading_dialog.LoadingDialog
import com.petrs.smartlab.utils.network.State
import org.koin.androidx.viewmodel.ext.android.viewModel

class EmailCodeFragment : BaseFragment<FragmentEmailCodeBinding, EmailCodeViewModel>(
    FragmentEmailCodeBinding::inflate
) {
    private val args by navArgs<EmailCodeFragmentArgs>()
    private val email by lazy { args.email }

    override val viewModel: EmailCodeViewModel by viewModel()

    override fun initView() {
        viewModel.startTimer()

        binding.apply {
            etCode1.doAfterTextChanged {
                if (!etCode1.text.isNullOrEmpty() && !etCode2.text.isNullOrEmpty() && !etCode3.text.isNullOrEmpty() && !etCode4.text.isNullOrEmpty()) {
                    viewModel.signIn(
                        email,
                        "${etCode1.text}${etCode2.text}${etCode3.text}${etCode4.text}"
                    )
                }
                etCode2.requestFocus()
            }
            etCode2.doAfterTextChanged {
                if (!etCode1.text.isNullOrEmpty() && !etCode2.text.isNullOrEmpty() && !etCode3.text.isNullOrEmpty() && !etCode4.text.isNullOrEmpty()) {
                    viewModel.signIn(
                        email,
                        "${etCode1.text}${etCode2.text}${etCode3.text}${etCode4.text}"
                    )
                }
                etCode3.requestFocus()
            }
            etCode3.doAfterTextChanged {
                if (!etCode1.text.isNullOrEmpty() && !etCode2.text.isNullOrEmpty() && !etCode3.text.isNullOrEmpty() && !etCode4.text.isNullOrEmpty()) {
                    viewModel.signIn(
                        email,
                        "${etCode1.text}${etCode2.text}${etCode3.text}${etCode4.text}"
                    )
                }
                etCode4.requestFocus()
            }
            etCode4.doAfterTextChanged {
                if (!etCode1.text.isNullOrEmpty() && !etCode2.text.isNullOrEmpty() && !etCode3.text.isNullOrEmpty() && !etCode4.text.isNullOrEmpty()) {
                    viewModel.signIn(
                        email,
                        "${etCode1.text}${etCode2.text}${etCode3.text}${etCode4.text}"
                    )
                }
                etCode4.clearFocus()
            }
            tvSendCodeAgain.setOnClickListener {
                viewModel.sendNewCode(email)
                viewModel.startTimer()
                it.isEnabled = false
            }
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    override fun observeViewModel() {
        viewModel.apply {
            timer.observe { second ->
                if (second == 0) {
                    binding.tvSendCodeAgain.text = getString(R.string.text_send_code_again)
                    binding.tvSendCodeAgain.isEnabled = true
                } else {
                    binding.tvSendCodeAgain.text =
                        getString(R.string.text_send_code_again_with_time, second)
                }
            }

            token.observe { state ->
                when (state) {
                    is DomainResult.Success -> {
                        saveToken(state.data.token)
                        (requireActivity() as LoginActivity).hideLoading()
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
                            (requireActivity() as LoginActivity).showLoading()
                    }
                }
            }

            sendCodeToEmail.observe { state ->
                when (state) {
                    is DomainResult.Success -> {
                        startTimer()
                        (requireActivity() as LoginActivity).hideLoading()
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
                            (requireActivity() as LoginActivity).showLoading()
                    }
                }
            }

            saveToken.observe { state ->
                when (state) {
                    is State.Success -> {
                        findNavController().navigate(EmailCodeFragmentDirections.actionEmailCodeFragmentToCreatePasswordFragment())
                    }
                    is State.Error -> {}
                    is State.Loading -> {}
                }
            }
        }
    }
}