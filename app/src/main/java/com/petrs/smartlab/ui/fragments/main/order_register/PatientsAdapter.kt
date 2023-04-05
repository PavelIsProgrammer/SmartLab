package com.petrs.smartlab.ui.fragments.main.order_register

import android.view.LayoutInflater
import android.view.ViewGroup
import com.petrs.smartlab.databinding.ItemOrderRegistrationPatientBinding
import com.petrs.smartlab.domain.models.CatalogItemDomain
import com.petrs.smartlab.ui.base.BaseListAdapter
import com.petrs.smartlab.ui.fragments.main.order_register.models.CartItem
import com.petrs.smartlab.ui.fragments.main.order_register.models.PatientItem

class PatientsAdapter(
    private val eventListener: PatientsEventListener
) : BaseListAdapter<Pair<PatientItem, List<CartItem>>, PatientsViewHolder>(
    areItemsTheSameCompare = { oldItem, newItem ->
        oldItem.first.profile.id == newItem.first.profile.id
    }
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PatientsViewHolder(
        binding = ItemOrderRegistrationPatientBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ),
        eventListener
    )

    override fun onBindViewHolder(holder: PatientsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}