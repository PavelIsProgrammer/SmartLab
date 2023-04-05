package com.petrs.smartlab.ui.base.loading_dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.petrs.smartlab.databinding.FragmentLoadingDialogBinding

class LoadingDialog : DialogFragment() {

    private lateinit var binding: FragmentLoadingDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoadingDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        val TAG = "${LoadingDialog::class.simpleName}_TAG"

        fun getInstance() = LoadingDialog()
    }
}