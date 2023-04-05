package com.petrs.smartlab.ui.fragments.main.order_register

import android.util.Log
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.petrs.smartlab.*
import com.petrs.smartlab.data.ErrorType
import com.petrs.smartlab.databinding.FragmentOrderRegisterBinding
import com.petrs.smartlab.domain.DomainResult
import com.petrs.smartlab.domain.LoadingState
import com.petrs.smartlab.ui.activities.main.MainActivity
import com.petrs.smartlab.ui.base.BaseFragment
import com.petrs.smartlab.ui.base.error_dialog.ErrorDialog
import com.petrs.smartlab.ui.base.error_dialog.ErrorDialogParams
import com.petrs.smartlab.ui.base.loading_dialog.LoadingDialog
import com.petrs.smartlab.ui.fragments.main.order_register.models.CartItem
import com.petrs.smartlab.ui.fragments.main.order_register.models.PatientItem
import com.petrs.smartlab.utils.network.State
import kotlinx.coroutines.delay
import org.koin.androidx.viewmodel.ext.android.viewModel

class OrderRegisterFragment : BaseFragment<FragmentOrderRegisterBinding, OrderRegisterViewModel>(
    FragmentOrderRegisterBinding::inflate
) {
    override val viewModel: OrderRegisterViewModel by viewModel()

    private lateinit var patientsAdapter: PatientsAdapter

    override fun initView() {
        viewModel.getCart()
        viewModel.getPatients()

        patientsAdapter = PatientsAdapter(
            object : PatientsEventListener {
                override fun analysisCheckedChanged(pair: Pair<PatientItem, List<CartItem>>) {
                    viewModel.submitNewAnalyzes(viewModel.staticResult.map {
                        if (it.first.profile.id == pair.first.profile.id)
                            pair
                        else
                            it
                    }) { validateOrder() }
                }

                override fun onCloseClicked(patient: PatientItem) {
                    val newList =
                        patientsAdapter.currentList.filter { it.first.profile.id != patient.profile.id }
                    viewModel.submitNewAnalyzes(newList) { validateOrder() }
                    if (newList.size > 1) {
                        patientsAdapter.submitList(newList)
                    } else {
                        binding.apply {
                            rvPatients.isVisible = false
                            constraintOnePatient.isVisible = true

                            spinnerPatient.text =
                                "${newList[0].first.profile.lastName} ${newList[0].first.profile.firstName}"
                        }
                    }
                }
            }
        )

        binding.apply {
            rvPatients.adapter = patientsAdapter

            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }

            tvAddress.setOnClickListener {
                val dialog = FragmentAddressBottomSheetDialog.getInstance(
                    AddressBottomSheetDialogParams { address, saveAddress, addressTitle ->
                        tvAddress.text = address
                        tvAddress.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.black
                            )
                        )
                        viewModel.orderRequestBody.address = address
                        validateOrder()
                        if (saveAddress) {
                            //ToDo viewModel.saveAddress(addressTitle, address)
                        }
                    }
                )

                dialog.setStyle(
                    DialogFragment.STYLE_NO_TITLE,
                    R.style.BottomSheetDialogTheme
                )

                dialog.show(childFragmentManager, FragmentAddressBottomSheetDialog.TAG)
            }

            tvDateTime.setOnClickListener {
                val dialog = FragmentDateTimeBottomSheetDialog.getInstance(
                    DateTimeBottomSheetDialogParams {
                        tvDateTime.text = it
                        tvDateTime.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.black
                            )
                        )
                        viewModel.orderRequestBody.dateTime = it
                        validateOrder()
                    }
                )

                dialog.setStyle(
                    DialogFragment.STYLE_NO_TITLE,
                    R.style.BottomSheetDialogTheme
                )

                dialog.show(childFragmentManager, FragmentDateTimeBottomSheetDialog.TAG)
            }

            spinnerPatient.setOnClickListener {
                val dialog = FragmentSelectPatientsBottomSheetDialog.getInstance(
                    SelectPatientsBottomSheetDialogParams(
                        viewModel.staticPatients.mapIndexed { index, it ->
                            PatientItem(
                                it,
                                selected = index == 0
                            )
                        }, {
                            if (it.size > 1) {
                                rvPatients.isVisible = true
                                constraintOnePatient.isVisible = false

                                viewModel.submitNewAnalyzes(it.map { patientItem ->
                                    Pair(
                                        patientItem,
                                        viewModel.staticCart.map { catalog ->
                                            CartItem(
                                                catalog,
                                                true
                                            )
                                        }
                                    )
                                }
                                ) {
                                    validateOrder()
                                }
                                Log.d("TAG-TAG", viewModel.staticResult.toString())

                                patientsAdapter.submitList(
                                    viewModel.staticResult
                                )
                            } else {
                                constraintOnePatient.isVisible = true
                                rvPatients.isVisible = false

                                spinnerPatient.text =
                                    "${it[0].profile.lastName} ${it[0].profile.firstName}"
                            }
                        }, {
                            val dialog = FragmentNewPatientBottomSheetDialog.getInstance(
                                NewPatientBottomSheetDialogParams { name, lastName, midName, birthDate, sexOrientation ->
                                    viewModel.createProfile(
                                        name,
                                        lastName,
                                        midName,
                                        birthDate,
                                        sexOrientation
                                    )
                                }
                            )

                            dialog.setStyle(
                                DialogFragment.STYLE_NO_TITLE,
                                R.style.BottomSheetDialogTheme
                            )

                            dialog.show(
                                childFragmentManager,
                                FragmentNewPatientBottomSheetDialog.TAG
                            )
                        }
                    )
                )

                dialog.setStyle(
                    DialogFragment.STYLE_NO_TITLE,
                    R.style.BottomSheetDialogTheme
                )

                dialog.show(childFragmentManager, FragmentSelectPatientsBottomSheetDialog.TAG)
            }

            btnAddNewPatient.setOnClickListener {
                val dialog = FragmentNewPatientBottomSheetDialog.getInstance(
                    NewPatientBottomSheetDialogParams { name, lastName, midName, birthDate, sexOrientation ->
                        viewModel.createProfile(name, lastName, midName, birthDate, sexOrientation)
                    }
                )

                dialog.setStyle(
                    DialogFragment.STYLE_NO_TITLE,
                    R.style.BottomSheetDialogTheme
                )

                dialog.show(childFragmentManager, FragmentNewPatientBottomSheetDialog.TAG)
            }

            etComment.doAfterTextChanged {
                viewModel.orderRequestBody.comment = if (!etComment.text.isNullOrBlank()) etComment.text.toString() else ""
            }

            etPhone.doAfterTextChanged {
                viewModel.orderRequestBody.phone = if (!etPhone.text.isNullOrBlank()) etPhone.text.toString() else ""
                validateOrder()
            }

            btnCreateOrder.setOnClickListener {
                viewModel.createOrder()
            }
        }
    }

    override fun observeViewModel() {
        viewModel.apply {
            cart.observe { state ->
                when (state) {
                    is State.Success -> {
                        staticCart = state.data
                    }
                    is State.Error -> {}
                    State.Loading -> {}
                }
            }
            result.observe { state ->
                when (state) {
                    is State.Success -> {
                        var analyzesQuantity = 0
                        var analyzesSum = 0

                        state.data.forEach { pair ->
                            pair.second.forEach { cartItem ->
                                if (cartItem.selected) {
                                    analyzesQuantity++
                                    analyzesSum += cartItem.catalogItem.price.toInt()
                                }
                            }
                        }

                        binding.tvAnalyzesQuantity.text = formatQuantity(analyzesQuantity)
                        binding.tvAnalyzesSum.text =
                            getString(R.string.price_rub, analyzesSum.toString())
                    }
                    is State.Error -> {}
                    State.Loading -> {}
                }
            }
            profileInfo.observe { state ->
//                val loadingDialog = LoadingDialog.getInstance()
                when (state) {
                    is DomainResult.Success -> {
                        saveProfile(state.data)
//                        loadingDialog.dismiss()
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
//                        if (state.state == LoadingState.REQUEST_LOADING)
//                            loadingDialog.show(childFragmentManager, LoadingDialog.TAG)
                    }
                }
            }
            patients.observe { state ->
                when (state) {
                    is State.Success -> {
                        staticPatients = state.data

                        if (state.data.isNotEmpty()) {
                            binding.spinnerPatient.text =
                                "${state.data[0].lastName} ${state.data[0].firstName}"
                            Glide.with(requireContext())
                                .load(if (state.data[0].sexOrientation == "Мужской") R.drawable.male else R.drawable.female)
                                .into(binding.ivSexOrientation)
                        }

                        submitNewAnalyzes(listOf(Pair(PatientItem(state.data[0], true), staticCart.map { CartItem(it, true) }))) {
                            validateOrder()
                        }
                    }
                    is State.Error -> {}
                    State.Loading -> {}
                }
            }
            createOrder.observe { state ->
                val loadingDialog = LoadingDialog.getInstance()
                when (state) {
                    is DomainResult.Success -> {
//                        loadingDialog.dismiss()
                        findNavController().navigate(OrderRegisterFragmentDirections.actionOrderRegisterFragmentToPaymentFragment())
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
//                        if (state.state == LoadingState.REQUEST_LOADING)
//                            loadingDialog.show(childFragmentManager, LoadingDialog.TAG)
                    }
                }
            }
        }
    }

    private fun formatQuantity(quantity: Int): String {
        var result = ""
        result = if (quantity % 10 == 1 && quantity != 11) {
            "$quantity анализ"
        } else if (quantity % 10 in 2..4 && quantity !in 12..14) {
            "$quantity анализаа"
        } else {
            "$quantity анализов"
        }
        return result
    }

    private fun validateOrder() {
        binding.btnCreateOrder.apply {
            Log.d("TAG", viewModel.orderRequestBody.toString())
            if (viewModel.orderRequestBody.address.isNotBlank() && viewModel.orderRequestBody.dateTime.isNotBlank() && viewModel.orderRequestBody.phone.isNotBlank() && viewModel.orderRequestBody.patients.isNotEmpty()) {
                background = ContextCompat.getDrawable(requireContext(), R.drawable.filled_btn)
                isEnabled = true
            } else {
                background = ContextCompat.getDrawable(requireContext(), R.drawable.blocked_btn)
                isEnabled = false
            }
        }
    }
}