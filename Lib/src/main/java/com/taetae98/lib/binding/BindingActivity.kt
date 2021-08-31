package com.taetae98.lib.binding

import android.os.Bundle
import android.os.PersistableBundle
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.taetae98.lib.base.BaseActivity

abstract class BindingActivity<VB: ViewDataBinding>(
    @LayoutRes
    layoutRes: Int
) : BaseActivity() {
    protected val binding: VB by lazy {
        DataBindingUtil.setContentView(this, layoutRes)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onCreateViewDataBinding()
    }

    protected fun onCreateViewDataBinding() {
        binding.lifecycleOwner = this
    }
}