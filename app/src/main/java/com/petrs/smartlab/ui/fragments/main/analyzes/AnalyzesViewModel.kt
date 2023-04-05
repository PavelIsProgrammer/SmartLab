package com.petrs.smartlab.ui.fragments.main.analyzes

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petrs.smartlab.domain.DomainResult
import com.petrs.smartlab.domain.LoadingState
import com.petrs.smartlab.domain.models.CatalogItemDomain
import com.petrs.smartlab.domain.models.NewsItemDomain
import com.petrs.smartlab.domain.useCases.GetCartUseCase
import com.petrs.smartlab.domain.useCases.GetCatalogUseCase
import com.petrs.smartlab.domain.useCases.GetNewsUseCase
import com.petrs.smartlab.domain.useCases.SaveCartUseCase
import com.petrs.smartlab.ui.fragments.main.analyzes.models.Category
import com.petrs.smartlab.utils.network.State
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.math.log

class AnalyzesViewModel(
    private val getCatalogUseCase: GetCatalogUseCase,
    private val getNewsUseCase: GetNewsUseCase,
    private val getCartUseCase: GetCartUseCase,
    private val saveCartUseCase: SaveCartUseCase
) : ViewModel() {

    private val _catalog: MutableStateFlow<DomainResult<List<CatalogItemDomain>>> =
        MutableStateFlow(DomainResult.Loading(LoadingState.INITIAL))
    val catalog = _catalog.asStateFlow()

    private val _news: MutableStateFlow<DomainResult<List<NewsItemDomain>>> =
        MutableStateFlow(DomainResult.Loading(LoadingState.INITIAL))
    val news = _news.asStateFlow()

    private val _searchResult: MutableStateFlow<Pair<String, List<CatalogItemDomain>>> = MutableStateFlow(Pair("", emptyList()))
    val searchResult = _searchResult.asStateFlow()

    val categoriesList: ArrayList<Category> = arrayListOf()
    val analyzesList: ArrayList<List<CatalogItemDomain>> = arrayListOf()

    private val _cartSum: MutableStateFlow<Int> = MutableStateFlow(0)
    val carSum = _cartSum.asStateFlow()

    fun getNews() = viewModelScope.launch {
        _news.value = DomainResult.Loading(LoadingState.REQUEST_LOADING)
        _news.value = getNewsUseCase()
    }

    fun getCatalog() = viewModelScope.launch {
        _catalog.value = DomainResult.Loading(LoadingState.REQUEST_LOADING)
        _catalog.value = getCatalogUseCase()
    }

    fun sortCategories() {
        val state = catalog.value
        val cart = getCartUseCase()
        categoriesList.clear()
        analyzesList.clear()
        if (state is DomainResult.Success && cart is State.Success) {
            Log.d("TAG", "${cart.data}")
            var latestList: List<CatalogItemDomain> = state.data
            while (latestList.isNotEmpty()) {
                val category = latestList[0].category
                categoriesList.add(Category(category))
                val sortedList = latestList.filter { it.category == category }
                cart.data.forEach { cartItem ->
                    sortedList.map {
                        if (it.id == cartItem.id) it.inCart = cartItem.inCart
                    }
                }
                analyzesList.add(sortedList)
                latestList = latestList.filter { it.category != category }
            }
        }
    }

    fun addInCart(item: CatalogItemDomain) {
        val cart = getCartUseCase()
        val newCart = arrayListOf<CatalogItemDomain>()
        if (cart is State.Success) {
            if (item.inCart > 0) {
                if (cart.data.find { it.id == item.id } == null) {
                    newCart.addAll(cart.data)
                    newCart.add(item)
                } else {
                    newCart.addAll(cart.data)
                    newCart.map { if (it.id == item.id) it.inCart = item.inCart }
                }
            } else {
                newCart.addAll(cart.data.filter { it.id != item.id })
            }
        }
        saveCartUseCase(newCart)
        getCartSum()
    }

    fun searchItems(text: String) {
        val state = catalog.value
        if (state is DomainResult.Success) {
            _searchResult.value = Pair(text, state.data.filter { it.title.contains(text) })
        }
    }

    fun getCartSum() = viewModelScope.launch {
        val cart = getCartUseCase()
        var sum = 0
        if (cart is State.Success) {
            cart.data.forEach {
                sum += it.price.toInt() * it.inCart
            }
        }
        _cartSum.value = sum
    }
}