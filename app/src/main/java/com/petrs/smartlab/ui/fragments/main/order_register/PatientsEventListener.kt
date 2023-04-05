package com.petrs.smartlab.ui.fragments.main.order_register

import com.petrs.smartlab.domain.models.CatalogItemDomain
import com.petrs.smartlab.ui.fragments.main.order_register.models.CartItem
import com.petrs.smartlab.ui.fragments.main.order_register.models.PatientItem

interface PatientsEventListener {
    fun analysisCheckedChanged(pair: Pair<PatientItem, List<CartItem>>)
    fun onCloseClicked(patient: PatientItem)
}