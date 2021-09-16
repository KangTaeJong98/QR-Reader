package com.taetae98.qrreader.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.taetae98.modules.library.navigation.NavigationFragment
import com.taetae98.qrreader.R
import com.taetae98.qrreader.databinding.FragmentMessageBinding
import com.taetae98.qrreader.interfaces.TabComponent
import com.taetae98.qrreader.viewmodel.BarcodeViewModel
import com.taetae98.qrreader.viewmodel.MessageViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MessageFragment : NavigationFragment<FragmentMessageBinding>(R.layout.fragment_message), TabComponent {
    override val tabIcon = R.drawable.ic_round_message_24

    private val barcodeViewModel by viewModels<BarcodeViewModel>()
    private val messageViewModel by activityViewModels<MessageViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onObserveTelNumber()
        onObserveMessage()
    }

    private fun onObserveTelNumber() {
        messageViewModel.telNumber.observe(viewLifecycleOwner) {
            barcodeViewModel.barcode.value = messageViewModel.toBarcode()
        }
    }

    private fun onObserveMessage() {
        messageViewModel.message.observe(viewLifecycleOwner) {
            barcodeViewModel.barcode.value = messageViewModel.toBarcode()
        }
    }

    override fun onCreateViewDataBinding() {
        super.onCreateViewDataBinding()
        binding.barcodeViewModel = barcodeViewModel
        binding.messageViewModel = messageViewModel
    }
}