package com.taetae98.qrreader.fragment

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.taetae98.module.binding.BindingFragment
import com.taetae98.qrreader.R
import com.taetae98.qrreader.application.TAG
import com.taetae98.qrreader.databinding.FragmentCalendarBinding
import com.taetae98.qrreader.interfaces.TabComponent
import com.taetae98.qrreader.viewmodel.BarcodeViewModel
import com.taetae98.qrreader.viewmodel.CalendarViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class CalendarFragment : BindingFragment<FragmentCalendarBinding>(R.layout.fragment_calendar), TabComponent {
    override val tabIcon = R.drawable.ic_round_calendar_today_24

    private val barcodeViewModel by viewModels<BarcodeViewModel>()
    private val calendarViewModel by activityViewModels<CalendarViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onObserveTitle()
        onObserveDescription()
        onObserveLocation()
        onObserveAllDay()
        onObserveBeginYear()
        onObserveBeginMonth()
        onObserveBeginDayOfMonth()
        onObserveBeginHourOfDay()
        onObserveBeginMinute()
        onObserveEndYear()
        onObserveEndMonth()
        onObserveEndDayOfMonth()
        onObserveEndHourOfDay()
        onObserveEndMinute()
    }

    private fun onObserveTitle() {
        calendarViewModel.title.observe(viewLifecycleOwner) {
            barcodeViewModel.barcode.value = calendarViewModel.toBarcode()
        }
    }

    private fun onObserveDescription() {
        calendarViewModel.description.observe(viewLifecycleOwner) {
            barcodeViewModel.barcode.value = calendarViewModel.toBarcode()
        }
    }

    private fun onObserveLocation() {
        calendarViewModel.location.observe(viewLifecycleOwner) {
            barcodeViewModel.barcode.value = calendarViewModel.toBarcode()
        }
    }

    private fun onObserveAllDay() {
        calendarViewModel.allDay.observe(viewLifecycleOwner) {
            barcodeViewModel.barcode.value = calendarViewModel.toBarcode()
        }
    }

    private fun onObserveBeginYear() {
        calendarViewModel.beginYear.observe(viewLifecycleOwner) {
            barcodeViewModel.barcode.value = calendarViewModel.toBarcode()
        }
    }

    private fun onObserveBeginMonth() {
        calendarViewModel.beginMonth.observe(viewLifecycleOwner) {
            barcodeViewModel.barcode.value = calendarViewModel.toBarcode()
        }
    }

    private fun onObserveBeginDayOfMonth() {
        calendarViewModel.beginDayOfMonth.observe(viewLifecycleOwner) {
            barcodeViewModel.barcode.value = calendarViewModel.toBarcode()
        }
    }

    private fun onObserveBeginHourOfDay() {
        calendarViewModel.beginHourOfDay.observe(viewLifecycleOwner) {
            barcodeViewModel.barcode.value = calendarViewModel.toBarcode()
        }
    }

    private fun onObserveBeginMinute() {
        calendarViewModel.beginMinute.observe(viewLifecycleOwner) {
            barcodeViewModel.barcode.value = calendarViewModel.toBarcode()
        }
    }

    private fun onObserveEndYear() {
        calendarViewModel.endYear.observe(viewLifecycleOwner) {
            barcodeViewModel.barcode.value = calendarViewModel.toBarcode()
        }
    }

    private fun onObserveEndMonth() {
        calendarViewModel.endMonth.observe(viewLifecycleOwner) {
            barcodeViewModel.barcode.value = calendarViewModel.toBarcode()
        }
    }

    private fun onObserveEndDayOfMonth() {
        calendarViewModel.endDayOfMonth.observe(viewLifecycleOwner) {
            barcodeViewModel.barcode.value = calendarViewModel.toBarcode()
        }
    }

    private fun onObserveEndHourOfDay() {
        calendarViewModel.endHourOfDay.observe(viewLifecycleOwner) {
            barcodeViewModel.barcode.value = calendarViewModel.toBarcode()
        }
    }

    private fun onObserveEndMinute() {
        calendarViewModel.endMinute.observe(viewLifecycleOwner) {
            barcodeViewModel.barcode.value = calendarViewModel.toBarcode()
        }
    }

    override fun onCreateViewDataBinding() {
        super.onCreateViewDataBinding()
        binding.barcodeViewModel = barcodeViewModel
        binding.calendarViewModel = calendarViewModel
        binding.setOnBeginDate { onBeginDate() }
        binding.setOnBeginTime { onBeginTime() }
        binding.setOnEndDate { onEndDate() }
        binding.setOnEndTime { onEndTime() }
    }

    private fun onBeginDate() {
        Log.d(TAG, "PASS")
        DatePickerDialog(requireContext(), { _, year, month, dayOfMonth ->
            calendarViewModel.beginYear.value = year
            calendarViewModel.beginMonth.value = month
            calendarViewModel.beginDayOfMonth.value = dayOfMonth
        }, calendarViewModel.beginYear.value!!, calendarViewModel.beginMonth.value!!, calendarViewModel.beginDayOfMonth.value!!).show()
    }

    private fun onBeginTime() {
        TimePickerDialog(requireContext(), {_, hourOfDay, minute ->
            calendarViewModel.beginHourOfDay.value = hourOfDay
            calendarViewModel.beginMinute.value = minute
        }, calendarViewModel.beginHourOfDay.value!!, calendarViewModel.beginMinute.value!!, true).show()
    }

    private fun onEndDate() {
        DatePickerDialog(requireContext(), { _, year, month, dayOfMonth ->
            calendarViewModel.endYear.value = year
            calendarViewModel.endMonth.value = month
            calendarViewModel.endDayOfMonth.value = dayOfMonth
        }, calendarViewModel.endYear.value!!, calendarViewModel.endMonth.value!!, calendarViewModel.endDayOfMonth.value!!).show()
    }

    private fun onEndTime() {
        TimePickerDialog(requireContext(), {_, hourOfDay, minute ->
            calendarViewModel.endHourOfDay.value = hourOfDay
            calendarViewModel.endMinute.value = minute
        }, calendarViewModel.endHourOfDay.value!!, calendarViewModel.endMinute.value!!, true).show()
    }
}