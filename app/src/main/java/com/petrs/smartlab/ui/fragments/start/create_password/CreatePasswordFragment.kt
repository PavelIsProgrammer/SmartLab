package com.petrs.smartlab.ui.fragments.start.create_password

import android.content.Intent
import android.util.Log
import androidx.navigation.fragment.findNavController
import com.petrs.smartlab.R
import com.petrs.smartlab.databinding.FragmentCreatePasswordBinding
import com.petrs.smartlab.ui.activities.main.MainActivity
import com.petrs.smartlab.ui.base.BaseFragment
import com.petrs.smartlab.utils.network.State
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreatePasswordFragment : BaseFragment<FragmentCreatePasswordBinding, CreatePasswordViewModel>(
    FragmentCreatePasswordBinding::inflate
) {

    override val viewModel: CreatePasswordViewModel by viewModel()

    override fun initView() {
        binding.apply {
            btnSkip.setOnClickListener {
                viewModel.checkProfile()
            }
            btn0.setOnClickListener {
                viewModel.addNumberToPassword(0)
            }
            btn1.setOnClickListener {
                viewModel.addNumberToPassword(1)
            }
            btn2.setOnClickListener {
                viewModel.addNumberToPassword(2)
            }
            btn3.setOnClickListener {
                viewModel.addNumberToPassword(3)
            }
            btn4.setOnClickListener {
                viewModel.addNumberToPassword(4)
            }
            btn5.setOnClickListener {
                viewModel.addNumberToPassword(5)
            }
            btn6.setOnClickListener {
                viewModel.addNumberToPassword(6)
            }
            btn7.setOnClickListener {
                viewModel.addNumberToPassword(7)
            }
            btn8.setOnClickListener {
                viewModel.addNumberToPassword(8)
            }
            btn9.setOnClickListener {
                viewModel.addNumberToPassword(9)
            }
            btnDeleteLast.setOnClickListener {
                viewModel.deleteLast()
            }
        }
    }

    override fun observeViewModel() {
        viewModel.apply {
            password.observe {
                when (it.length) {
                    0 -> {
                        binding.iv1.setBackgroundResource(R.drawable.create_password_not_collected)
                        binding.iv2.setBackgroundResource(R.drawable.create_password_not_collected)
                        binding.iv3.setBackgroundResource(R.drawable.create_password_not_collected)
                        binding.iv4.setBackgroundResource(R.drawable.create_password_not_collected)
                    }
                    1 -> {
                        binding.iv1.setBackgroundResource(R.drawable.create_password_collected)
                        binding.iv2.setBackgroundResource(R.drawable.create_password_not_collected)
                        binding.iv3.setBackgroundResource(R.drawable.create_password_not_collected)
                        binding.iv4.setBackgroundResource(R.drawable.create_password_not_collected)
                    }
                    2 -> {
                        binding.iv2.setBackgroundResource(R.drawable.create_password_collected)
                        binding.iv3.setBackgroundResource(R.drawable.create_password_not_collected)
                        binding.iv4.setBackgroundResource(R.drawable.create_password_not_collected)
                    }
                    3 -> {
                        binding.iv3.setBackgroundResource(R.drawable.create_password_collected)
                        binding.iv4.setBackgroundResource(R.drawable.create_password_not_collected)
                    }
                    4 -> {
                        binding.iv4.setBackgroundResource(R.drawable.create_password_collected)
                        checkProfile()
                    }
                }
            }

            profileIsCreated.observe { state ->
                when (state) {
                    is State.Success -> {
                        if (state.data.isNotEmpty()) {
                            startMainActivity()
                        } else {
                            findNavController().navigate(CreatePasswordFragmentDirections.actionCreatePasswordFragmentToCreateClientCardFragment())
                        }
                    }
                    is State.Error -> {}
                    State.Loading -> {}
                }

            }
        }
    }

    private fun startMainActivity() {
        val i = Intent(requireContext(), MainActivity::class.java)
        startActivity(i)
    }
}