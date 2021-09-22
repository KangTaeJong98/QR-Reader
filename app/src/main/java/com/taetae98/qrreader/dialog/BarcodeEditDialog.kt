package com.taetae98.qrreader.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.zxing.BarcodeFormat
import com.taetae98.modules.library.navigation.NavigationDialogFragment
import com.taetae98.qrreader.R
import com.taetae98.qrreader.databinding.DialogBarcodeEditBinding
import com.taetae98.qrreader.repository.BarcodeDataRepository
import com.taetae98.qrreader.viewmodel.BarcodeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class BarcodeEditDialog : NavigationDialogFragment<DialogBarcodeEditBinding>(R.layout.dialog_barcode_edit) {
    private val args by navArgs<BarcodeEditDialogArgs>()
    private val barcodeData by lazy { args.barcodeData }

    private val codeViewModel by viewModels<BarcodeViewModel> { BarcodeViewModel.Factory(barcodeData, this) }

    @Inject
    lateinit var barcodeDataRepository: BarcodeDataRepository

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
    }

    override fun onCreateViewDataBinding() {
        super.onCreateViewDataBinding()
        binding.viewModel = codeViewModel
        binding.setOnFinish {
            onFinish()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        onCreateFormatInputLayout()
        return binding.root
    }

    private fun onCreateFormatInputLayout() {
        with(binding.formatInputLayout.editText as AutoCompleteTextView) {
            val values = BarcodeFormat.values()
            setAdapter(
                ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_dropdown_item,
                    values.map {
                        it.name.replace("_", " ")
                    }
                )
            )

            setOnItemClickListener { _, _, i, _ ->
                codeViewModel.format.value = values[i]
            }

            setText(BarcodeFormat.QR_CODE.name, false)
        }
    }

    private fun onFinish() {
        CoroutineScope(Dispatchers.IO).launch {
            barcodeDataRepository.update(
                barcodeData.copy(
                    barcode = codeViewModel.barcode.value!!,
                    format = codeViewModel.format.value!!,
                    name = codeViewModel.name.value!!,
                    time = System.currentTimeMillis()
                )
            )

            withContext(Dispatchers.Main) {
                findNavController().navigateUp()
            }
        }
    }
}