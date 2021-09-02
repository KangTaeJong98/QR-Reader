package com.taetae98.qrreader.fragment

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.google.zxing.integration.android.IntentIntegrator
import com.taetae98.module.binding.BindingFragment
import com.taetae98.qrreader.R
import com.taetae98.qrreader.databinding.FragmentScanBinding
import com.taetae98.qrreader.manager.SimpleClipboardManager
import com.taetae98.qrreader.viewmodel.CodeViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ScanFragment : BindingFragment<FragmentScanBinding>(R.layout.fragment_scan) {
    private val codeViewModel by viewModels<CodeViewModel>()

    private val onScanResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        when(it.resultCode) {
            Activity.RESULT_OK -> {
                IntentIntegrator.parseActivityResult(it.resultCode, it.data).also { result ->
                    codeViewModel.barcode.value = result.contents
                }
            }
        }
    }

    @Inject
    lateinit var simpleClipboardManager: SimpleClipboardManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        onCreateSupportActionBar()

        return binding.root
    }

    override fun onCreateViewDataBinding() {
        super.onCreateViewDataBinding()
        binding.viewModel = codeViewModel
        binding.setOnScan { onScan() }
    }

    private fun onCreateSupportActionBar() {
        setSupportActionBar(binding.toolbar)
    }

    private fun onScan() {
        onScanResult.launch(IntentIntegrator.forSupportFragment(this).apply {
            setPrompt("")
            setBeepEnabled(false)
        }.createScanIntent())
    }
}