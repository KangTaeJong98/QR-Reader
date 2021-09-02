package com.taetae98.qrreader.fragment

import androidx.fragment.app.viewModels
import com.taetae98.module.binding.BindingFragment
import com.taetae98.qrreader.R
import com.taetae98.qrreader.databinding.FragmentTextBinding
import com.taetae98.qrreader.interfaces.TabComponent
import com.taetae98.qrreader.viewmodel.BarcodeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TextFragment : BindingFragment<FragmentTextBinding>(R.layout.fragment_text), TabComponent {
    override val tabIcon = R.drawable.ic_round_text_fields_24

    private val barcodeViewModel by viewModels<BarcodeViewModel>()

    override fun onCreateViewDataBinding() {
        super.onCreateViewDataBinding()
        with(binding) {
            viewModel = barcodeViewModel
            setOnQR { onQR() }
        }
    }

    private fun onQR() {

    }
}