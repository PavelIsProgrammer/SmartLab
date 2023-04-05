package com.petrs.smartlab.ui.fragments.main.order_payed

import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.petrs.smartlab.R
import com.petrs.smartlab.databinding.FragmentOrderPayedBinding
import com.petrs.smartlab.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


class OrderPayedFragment : BaseFragment<FragmentOrderPayedBinding, OrderPayedViewModel>(
    FragmentOrderPayedBinding::inflate
) {
    override val viewModel: OrderPayedViewModel by viewModel()

    override fun initView() {
        binding.apply {
            //108..end
            val span = SpannableString(getString(R.string.text_order_payed_description))
            val clickableSpan: ClickableSpan = object : ClickableSpan() {
                override fun onClick(textView: View) {
                    Toast.makeText(requireContext(), "Условия сдачи", Toast.LENGTH_SHORT).show()
                }

                override fun updateDrawState(ds: TextPaint) {
                    super.updateDrawState(ds)
                    ds.isUnderlineText = false
                }
            }
            span.setSpan(clickableSpan, 106, span.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

            tvDescription.text = span
            tvDescription.movementMethod = LinkMovementMethod.getInstance()
            tvDescription.highlightColor = Color.TRANSPARENT

            btnGoToMain.setOnClickListener {
                findNavController().navigate(OrderPayedFragmentDirections.actionOrderPayedFragmentToAnalyzesFragment())
            }
        }
    }

    override fun observeViewModel() {
        viewModel.apply {

        }
    }

}