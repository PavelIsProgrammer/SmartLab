package com.petrs.smartlab.ui.fragments.main.order_register

import android.view.LayoutInflater
import android.widget.CheckBox
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.textview.MaterialTextView
import com.petrs.smartlab.R
import com.petrs.smartlab.databinding.ItemOrderRegistrationPatientBinding
import com.petrs.smartlab.domain.models.CatalogItemDomain
import com.petrs.smartlab.ui.fragments.main.order_register.models.CartItem
import com.petrs.smartlab.ui.fragments.main.order_register.models.PatientItem

class PatientsViewHolder(
    private val binding: ItemOrderRegistrationPatientBinding,
    private val eventListener: PatientsEventListener
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(pair: Pair<PatientItem, List<CartItem>>) {
        binding.apply {
            spinnerPatient.text = "${pair.first.profile.lastName} ${pair.first.profile.firstName}"
            Glide.with(root.context)
                .load(if (pair.first.profile.sexOrientation == "Мужской") R.drawable.male else R.drawable.female)
                .into(binding.ivSexOrientation)
            pair.second.forEach {
                val view = LayoutInflater.from(root.context).inflate(R.layout.item_patient_analysis, null)
                val check = view.findViewById<AppCompatCheckBox>(R.id.cb_analysis)
                val price = view.findViewById<MaterialTextView>(R.id.tv_price)

                check.text = it.catalogItem.title
                price.text = root.context.getString(R.string.price_rub, it.catalogItem.price)

                check.setOnCheckedChangeListener { _, b ->
                    val newListAnalyzes = pair.second
                    newListAnalyzes.map { mapItem -> if (mapItem.catalogItem.id == it.catalogItem.id) mapItem.selected = b }
                    eventListener.analysisCheckedChanged(Pair(pair.first, newListAnalyzes))
                }

                linearAnalyzes.addView(view)
            }

            btnClose.setOnClickListener {
                eventListener.onCloseClicked(pair.first)
            }
        }
    }
}