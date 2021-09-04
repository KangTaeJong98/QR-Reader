package com.taetae98.qrreader.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.taetae98.module.binding.BindingFragment
import com.taetae98.qrreader.R
import com.taetae98.qrreader.databinding.FragmentContactBinding
import com.taetae98.qrreader.interfaces.TabComponent
import com.taetae98.qrreader.viewmodel.BarcodeViewModel
import com.taetae98.qrreader.viewmodel.ContactViewModel

class ContactFragment : BindingFragment<FragmentContactBinding>(R.layout.fragment_contact), TabComponent {
    override val tabIcon = R.drawable.ic_round_perm_contact_calendar_24

    private val barcodeViewModel by viewModels<BarcodeViewModel>()
    private val contactViewModel by activityViewModels<ContactViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onObserveFirstName()
        onObserveLastName()
        onObserveMobileTelNumber()
        onObservePersonalTelNumber()
        onObservePersonalEmail()
        onObserveWebSite()
        onObserveCompany()
        onObserveCompanyPosition()
        onObserveCompanyTelNumber()
        onObserveCompanyEmail()
    }

    private fun onObserveFirstName() {
        contactViewModel.firstName.observe(viewLifecycleOwner) {
            barcodeViewModel.barcode.value = contactViewModel.toBarcode()
        }
    }

    private fun onObserveLastName() {
        contactViewModel.lastName.observe(viewLifecycleOwner) {
            barcodeViewModel.barcode.value = contactViewModel.toBarcode()
        }
    }

    private fun onObserveMobileTelNumber() {
        contactViewModel.mobileTelNumber.observe(viewLifecycleOwner) {
            barcodeViewModel.barcode.value = contactViewModel.toBarcode()
        }
    }

    private fun onObservePersonalTelNumber() {
        contactViewModel.personalTelNumber.observe(viewLifecycleOwner) {
            barcodeViewModel.barcode.value = contactViewModel.toBarcode()
        }
    }

    private fun onObservePersonalEmail() {
        contactViewModel.personalEmail.observe(viewLifecycleOwner) {
            barcodeViewModel.barcode.value = contactViewModel.toBarcode()
        }
    }

    private fun onObserveWebSite() {
        contactViewModel.webSite.observe(viewLifecycleOwner) {
            barcodeViewModel.barcode.value = contactViewModel.toBarcode()
        }
    }

    private fun onObserveCompany() {
        contactViewModel.company.observe(viewLifecycleOwner) {
            barcodeViewModel.barcode.value = contactViewModel.toBarcode()
        }
    }

    private fun onObserveCompanyPosition() {
        contactViewModel.companyPosition.observe(viewLifecycleOwner) {
            barcodeViewModel.barcode.value = contactViewModel.toBarcode()
        }
    }

    private fun onObserveCompanyTelNumber() {
        contactViewModel.companyTelNumber.observe(viewLifecycleOwner) {
            barcodeViewModel.barcode.value = contactViewModel.toBarcode()
        }
    }

    private fun onObserveCompanyEmail() {
        contactViewModel.companyEmail.observe(viewLifecycleOwner) {
            barcodeViewModel.barcode.value = contactViewModel.toBarcode()
        }
    }

    override fun onCreateViewDataBinding() {
        super.onCreateViewDataBinding()
        binding.barcodeViewModel = barcodeViewModel
        binding.contactViewModel = contactViewModel
    }
}