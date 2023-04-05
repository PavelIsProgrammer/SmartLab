package com.petrs.smartlab.ui.activities.login

import androidx.navigation.fragment.NavHostFragment
import com.petrs.smartlab.R
import com.petrs.smartlab.databinding.ActivityLoginBinding
import com.petrs.smartlab.ui.base.BaseActivity
import com.petrs.smartlab.ui.base.loading_dialog.LoadingDialog
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>(
    ActivityLoginBinding::inflate
) {

    override val viewModel: LoginViewModel by viewModel()

    private val loadingDialog = LoadingDialog.getInstance()

    override fun initView() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        val navController = navHostFragment.navController
        navController.navigate(R.id.splashFragment)
    }

    fun showLoading() {
        if (loadingDialog.dialog?.isShowing == false) loadingDialog.show(supportFragmentManager, LoadingDialog.TAG)
    }

    fun hideLoading() {
        if (loadingDialog.dialog?.isShowing == true) loadingDialog.dismiss()
    }

    override fun observeViewModel() {}
}