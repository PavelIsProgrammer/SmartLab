package com.petrs.smartlab.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch

abstract class BaseActivity<VB : ViewBinding, VM : ViewModel>(
    private val inflateBinding: (LayoutInflater) -> VB
) : AppCompatActivity() {

    private var _binding: VB? = null
    val binding get() = checkNotNull(_binding)

    protected abstract val viewModel: VM

    protected abstract fun initView()
    protected abstract fun observeViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)

        _binding = inflateBinding.invoke(layoutInflater)
        setContentView(binding.root)

        initView()
        observeViewModel()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    protected fun <T> Flow<T>.observe(collector: FlowCollector<T>) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                this@observe.collect(collector)
            }
        }
    }
}