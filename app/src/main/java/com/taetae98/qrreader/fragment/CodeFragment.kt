package com.taetae98.qrreader.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.google.zxing.BarcodeFormat
import com.taetae98.module.binding.BindingFragment
import com.taetae98.qrreader.R
import com.taetae98.qrreader.databinding.FragmentCodeBinding
import com.taetae98.qrreader.interfaces.TabComponent
import com.taetae98.qrreader.viewmodel.BarcodeViewModel
import com.taetae98.qrreader.viewmodel.CodeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CodeFragment : BindingFragment<FragmentCodeBinding>(R.layout.fragment_code), TabComponent {
    override val tabIcon = R.drawable.ic_round_text_fields_24

    private val codeViewModel by activityViewModels<CodeViewModel>()
    private val barcodeViewModel by viewModels<BarcodeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onObserveCode()
        onObserveFormat()
    }

    private fun onObserveCode() {
        codeViewModel.code.observe(viewLifecycleOwner) {
            barcodeViewModel.barcode.value = it
        }
    }

    private fun onObserveFormat() {
        codeViewModel.format.observe(viewLifecycleOwner) {
            barcodeViewModel.format.value = it
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        onCreateFormatInputLayout()

        return binding.root
    }

    override fun onCreateViewDataBinding() {
        super.onCreateViewDataBinding()
        binding.barcodeViewModel = barcodeViewModel
        binding.codeViewModel = codeViewModel
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
}