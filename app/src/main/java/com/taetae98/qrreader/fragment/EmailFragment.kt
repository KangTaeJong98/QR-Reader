package com.taetae98.qrreader.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.taetae98.module.binding.BindingFragment
import com.taetae98.qrreader.R
import com.taetae98.qrreader.databinding.FragmentEmailBinding
import com.taetae98.qrreader.interfaces.TabComponent
import com.taetae98.qrreader.viewmodel.BarcodeViewModel
import com.taetae98.qrreader.viewmodel.EmailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EmailFragment : BindingFragment<FragmentEmailBinding>(R.layout.fragment_email), TabComponent {
    override val tabIcon = R.drawable.ic_round_email_24

    private val barcodeViewModel by viewModels<BarcodeViewModel>()
    private val emailViewModel by activityViewModels<EmailViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onObserveEmail()
        onObserveTitle()
        onObserveContent()
    }

    private fun onObserveEmail() {
        emailViewModel.email.observe(viewLifecycleOwner) {
            barcodeViewModel.barcode.value = emailViewModel.toBarcode()
        }
    }

    private fun onObserveTitle() {
        emailViewModel.title.observe(viewLifecycleOwner) {
            barcodeViewModel.barcode.value = emailViewModel.toBarcode()
        }
    }

    private fun onObserveContent() {
        emailViewModel.content.observe(viewLifecycleOwner) {
            barcodeViewModel.barcode.value = emailViewModel.toBarcode()
        }
    }

    override fun onCreateViewDataBinding() {
        super.onCreateViewDataBinding()
        binding.barcodeViewModel = barcodeViewModel
        binding.emailViewModel = emailViewModel
    }
}