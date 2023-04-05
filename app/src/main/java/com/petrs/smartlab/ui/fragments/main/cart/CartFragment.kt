package com.petrs.smartlab.ui.fragments.main.cart

import androidx.navigation.fragment.findNavController
import com.petrs.smartlab.R
import com.petrs.smartlab.databinding.FragmentCartBinding
import com.petrs.smartlab.domain.models.CatalogItemDomain
import com.petrs.smartlab.ui.base.BaseFragment
import com.petrs.smartlab.utils.network.State
import org.koin.androidx.viewmodel.ext.android.viewModel

class CartFragment : BaseFragment<FragmentCartBinding, CartViewModel>(
    FragmentCartBinding::inflate
) {
    override val viewModel: CartViewModel by viewModel()
    private lateinit var cartAdapter: CartAdapter

    override fun initView() {
        cartAdapter = CartAdapter(
            object : CartEventListener {
                override fun onAddClicked(item: CatalogItemDomain) {
                    viewModel.addInCart(item)
                }

                override fun onSubscriptionClicked(item: CatalogItemDomain) {
                    viewModel.addInCart(item)
                }
            }
        )

        viewModel.getCart()
        viewModel.getCartSum()
        binding.apply {
            rvCart.adapter = cartAdapter
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }

            btnTrash.setOnClickListener {
                viewModel.clearCart()
            }

            btnGoToOrder.setOnClickListener {
                findNavController().navigate(CartFragmentDirections.actionCartFragmentToOrderRegisterFragment())
            }
        }
    }

    override fun observeViewModel() {
        viewModel.apply {
            cart.observe { state ->
                when (state) {
                    is State.Success -> {
                        cartAdapter.submitList(state.data)
                    }
                    is State.Error -> {}
                    State.Loading -> {}
                }
            }
            carSum.observe {
                binding.tvSumPrice.text = getString(R.string.price_rub, it.toString())
            }
        }
    }

}