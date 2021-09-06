package com.taetae98.qrreader.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    stateHandle: SavedStateHandle
): ViewModel() {
    val title by lazy { stateHandle.getLiveData("CALENDAR_VIEW_MODEL_TITLE", "") }
    val description by lazy { stateHandle.getLiveData("CALENDAR_VIEW_MODEL_DESCRIPTION", "") }
    val location by lazy { stateHandle.getLiveData("CALENDAR_VIEW_MODEL_LOCATION", "") }
    val allDay by lazy { stateHandle.getLiveData("CALENDAR_VIEW_MODEL_ALL_DAY", false) }
    val beginYear by lazy { stateHandle.getLiveData<Int>("CALENDAR_VIEW_MODEL_BEGIN_YEAR") }
    val beginMonth by lazy { stateHandle.getLiveData<Int>("CALENDAR_VIEW_MODEL_BEGIN_MONTH") }
    val beginDayOfMonth by lazy { stateHandle.getLiveData<Int>("CALENDAR_VIEW_MODEL_BEGIN_DAY_OF_MONTH") }
    val beginHourOfDay by lazy { stateHandle.getLiveData<Int>("CALENDAR_VIEW_MODEL_BEGIN_HOUR_OF_DAY") }
    val beginMinute by lazy { stateHandle.getLiveData<Int>("CALENDAR_VIEW_MODEL_BEGIN_MINUTE") }
    val endYear by lazy { stateHandle.getLiveData<Int>("CALENDAR_VIEW_MODEL_END_YEAR") }
    val endMonth by lazy { stateHandle.getLiveData<Int>("CALENDAR_VIEW_MODEL_END_MONTH") }
    val endDayOfMonth by lazy { stateHandle.getLiveData<Int>("CALENDAR_VIEW_MODEL_END_DAY_OF_MONTH") }
    val endHourOfDay by lazy { stateHandle.getLiveData<Int>("CALENDAR_VIEW_MODEL_END_HOUR_OF_DAY") }
    val endMinute by lazy { stateHandle.getLiveData<Int>("CALENDAR_VIEW_MODEL_END_MINUTE") }

    init {
        val calendar = GregorianCalendar()
        beginYear.value = calendar[Calendar.YEAR]
        beginMonth.value = calendar[Calendar.MONTH]
        beginDayOfMonth.value = calendar[Calendar.DAY_OF_MONTH]
        beginHourOfDay.value = calendar[Calendar.HOUR_OF_DAY]
        beginMinute.value = calendar[Calendar.MINUTE]
        endYear.value = calendar[Calendar.YEAR]
        endMonth.value = calendar[Calendar.MONTH]
        endDayOfMonth.value = calendar[Calendar.DAY_OF_MONTH]
        endHourOfDay.value = calendar[Calendar.HOUR_OF_DAY]
        endMinute.value = calendar[Calendar.MINUTE]
    }

    fun toBarcode(): String {
        return if (allDay.value == true) {
            "BEGIN:VEVENT\n" +
            "SUMMARY:${title.value}\n" +
            "DTSTART;VALUE=DATE:${String.format("%04d", beginYear.value)}${String.format("%02d", beginMonth.value!! + 1)}${String.format("%02d", beginDayOfMonth.value)}\n" +
            "DTEND;VALUE=DATE:${String.format("%04d", endYear.value)}${String.format("%02d", endMonth.value!! + 1)}${String.format("%02d", endDayOfMonth.value)}\n" +
            "LOCATION:${location.value}\n" +
            "DESCRIPTION:${description.value}\n" +
            "END:VEVENT"
        } else {
            "BEGIN:VEVENT\n" +
            "SUMMARY:${title.value}\n" +
            "DTSTART:${String.format("%04d", beginYear.value)}${String.format("%02d", beginMonth.value!! + 1)}${String.format("%02d", beginDayOfMonth.value)}T${String.format("%02d", beginHourOfDay.value)}${String.format("%02d", beginMinute.value)}00Z\n" +
            "DTEND:${String.format("%04d", endYear.value)}${String.format("%02d", endMonth.value!! + 1)}${String.format("%02d", endDayOfMonth.value)}T${String.format("%02d", endHourOfDay.value)}${String.format("%02d", endMinute.value)}00Z\n" +
            "LOCATION:${location.value}\n" +
            "DESCRIPTION:${description.value}\n" +
            "END:VEVENT"
        }
    }
}