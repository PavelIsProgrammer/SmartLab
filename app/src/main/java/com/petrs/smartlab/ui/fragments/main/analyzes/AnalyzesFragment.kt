package com.petrs.smartlab.ui.fragments.main.analyzes

import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.appbar.AppBarLayout
import com.petrs.smartlab.R
import com.petrs.smartlab.data.ErrorType
import com.petrs.smartlab.databinding.FragmentAnalyzesBinding
import com.petrs.smartlab.domain.DomainResult
import com.petrs.smartlab.domain.LoadingState
import com.petrs.smartlab.domain.models.CatalogItemDomain
import com.petrs.smartlab.ui.activities.main.MainActivity
import com.petrs.smartlab.ui.base.BaseFragment
import com.petrs.smartlab.ui.base.error_dialog.ErrorDialog
import com.petrs.smartlab.ui.base.error_dialog.ErrorDialogParams
import com.petrs.smartlab.ui.base.loading_dialog.LoadingDialog
import com.petrs.smartlab.ui.fragments.main.analyzes.analyzes_adapter.AnalyzesAdapter
import com.petrs.smartlab.ui.fragments.main.analyzes.analyzes_adapter.CatalogEventListener
import com.petrs.smartlab.ui.fragments.main.analyzes.category_adapter.CategoryAdapter
import com.petrs.smartlab.ui.fragments.main.analyzes.category_adapter.CategoryEventListener
import com.petrs.smartlab.ui.fragments.main.analyzes.category_adapter.CategoryViewHolder
import com.petrs.smartlab.ui.fragments.main.analyzes.models.Category
import com.petrs.smartlab.ui.fragments.main.analyzes.news_adapter.NewsAdapter
import com.petrs.smartlab.ui.fragments.main.analyzes.search_adapter.SearchAdapter
import com.petrs.smartlab.ui.fragments.main.analyzes.search_adapter.SearchEventListener
import com.petrs.smartlab.utils.network.State
import org.koin.androidx.viewmodel.ext.android.viewModel

class AnalyzesFragment : BaseFragment<FragmentAnalyzesBinding, AnalyzesViewModel>(
    FragmentAnalyzesBinding::inflate
) {
    override val viewModel: AnalyzesViewModel by viewModel()

    private lateinit var lastClickedCategory: Category

    private val newsAdapter by lazy { NewsAdapter() }
    private lateinit var searchAdapter: SearchAdapter
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var analyzesAdapter: AnalyzesAdapter

    private var appBarIsExpanded = true

    override fun initView() {
        binding.srlAnalyzes.isEnabled = appBarIsExpanded

        val listener = AppBarLayout.OnOffsetChangedListener { _, verticalOffset ->
            appBarIsExpanded = verticalOffset == 0
            binding.srlAnalyzes.isEnabled = appBarIsExpanded
        }
        binding.appBarLayout.addOnOffsetChangedListener(listener)

        categoryAdapter = CategoryAdapter(
            object : CategoryEventListener {
                override fun onCategoryClick(category: Category) {
                    val position = viewModel.categoriesList.indexOf(category)
                    analyzesAdapter.submitList(viewModel.analyzesList[position])

                    val holder = binding.rvCategories.getChildViewHolder(
                        binding.rvCategories.getChildAt(
                            categoryAdapter.currentList.indexOf(
                                lastClickedCategory
                            )
                        )
                    ) as CategoryViewHolder
                    holder.deselectItem()
                    lastClickedCategory = category
                }
            }
        )

        analyzesAdapter = AnalyzesAdapter(
            object : CatalogEventListener {
                override fun onAddClicked(analysis: CatalogItemDomain) {
                    viewModel.addInCart(analysis)
                }

                override fun onItemClicked(analysis: CatalogItemDomain) {
                    val dialog = FragmentAnalysisBottomSheetDialog.getInstance(
                        AnalysisBottomSheetDialogParams(analysis) {
                            analysis.inCart++
                            viewModel.addInCart(analysis)
                            viewModel.getCatalog()

                            binding.rvAnalyzes.adapter = analyzesAdapter
                        }
                    )

                    dialog.setStyle(
                        DialogFragment.STYLE_NO_TITLE,
                        R.style.BottomSheetDialogTheme
                    )

                    dialog.show(childFragmentManager, FragmentAnalysisBottomSheetDialog.TAG)
                }
            }
        )

        viewModel.getNews()
        viewModel.getCatalog()
        viewModel.getCartSum()
        binding.apply {
            rvNews.adapter = newsAdapter
            rvCategories.adapter = categoryAdapter
            rvAnalyzes.adapter = analyzesAdapter

            srlAnalyzes.setOnRefreshListener {
                viewModel.getNews()
                viewModel.getCatalog()
                viewModel.getCartSum()

                rvNews.adapter = newsAdapter
                rvCategories.adapter = categoryAdapter
                rvAnalyzes.adapter = analyzesAdapter

                srlAnalyzes.isRefreshing = false
            }

            etSearch.setOnFocusChangeListener { _, b ->
                if (b) {
                    tvCancel.isVisible = true
                    btnClearSearch.isVisible = true
                    rvAnalyzes.isVisible = false
                    rvSearchResults.isVisible = true
                    appBarLayout.isVisible = false
                }
            }

            etSearch.doAfterTextChanged {
                if (!it.isNullOrBlank()) {
                    viewModel.searchItems(it.toString())
                } else {
                    searchAdapter.submitList(emptyList())
                }
            }

            tvCancel.setOnClickListener {
                tvCancel.isVisible = false
                btnClearSearch.isVisible = false
                rvAnalyzes.isVisible = true
                rvSearchResults.isVisible = false
                appBarLayout.isVisible = true

                etSearch.clearFocus()
            }

            btnClearSearch.setOnClickListener {
                etSearch.setText("")
            }

            btnGoToCart.setOnClickListener {
                findNavController().navigate(AnalyzesFragmentDirections.actionAnalyzesFragmentToCartFragment())
            }
        }
    }

    override fun observeViewModel() {
        viewModel.apply {
            news.observe { state ->
                val loadingDialog = LoadingDialog.getInstance()
                when (state) {
                    is DomainResult.Success -> {
                        newsAdapter.submitList(state.data)
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
//                        loadingDialog.show(childFragmentManager, LoadingDialog.TAG)
                    }
                }
            }
            catalog.observe { state ->
//                val loadingDialog = LoadingDialog.getInstance()
                when (state) {
                    is DomainResult.Success -> {
                        sortCategories()
                        categoryAdapter.submitList(categoriesList) {
                            lastClickedCategory = categoriesList[0]
                        }
                        analyzesAdapter.submitList(analyzesList[0])
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
//                        loadingDialog.show(childFragmentManager, LoadingDialog.TAG)
                    }
                }
            }
            carSum.observe {
                binding.apply {
                    if (it > 0) {
                        constraintCart.isVisible = true
                        tvCartSum.text = getString(R.string.price_rub, it.toString())
                    } else {
                        constraintCart.isVisible = false
                    }
                }
            }
            searchResult.observe {
                searchAdapter = SearchAdapter(it.first, object : SearchEventListener {
                    override fun onClick(item: CatalogItemDomain) {
                        val dialog = FragmentAnalysisBottomSheetDialog.getInstance(
                            AnalysisBottomSheetDialogParams(item) {
                                item.inCart++
                                viewModel.addInCart(item)
                                viewModel.getCatalog()
                                viewModel.getCartSum()

                                binding.rvAnalyzes.adapter = analyzesAdapter
                            }
                        )

                        dialog.setStyle(
                            DialogFragment.STYLE_NO_TITLE,
                            R.style.BottomSheetDialogTheme
                        )

                        dialog.show(childFragmentManager, FragmentAnalysisBottomSheetDialog.TAG)
                    }
                })
                binding.rvSearchResults.adapter = searchAdapter
                searchAdapter.submitList(it.second)
            }
        }
    }
}