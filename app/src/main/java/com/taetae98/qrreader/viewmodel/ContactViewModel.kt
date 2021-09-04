package com.taetae98.qrreader.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ContactViewModel @Inject constructor(
    stateHandle: SavedStateHandle
): ViewModel() {
    val firstName by lazy { stateHandle.getLiveData("CONTACT_VIEW_MODEL_FIRST_NAME", "") }
    val lastName by lazy { stateHandle.getLiveData("CONTACT_VIEW_MODEL_LAST_NAME", "") }
    val mobileTelNumber by lazy { stateHandle.getLiveData("CONTACT_VIEW_MODEL_MOBILE_TEL_NUMBER", "") }
    val personalTelNumber by lazy { stateHandle.getLiveData("CONTACT_VIEW_MODEL_PERSONAL_TEL_NUMBER", "") }
    val personalEmail by lazy { stateHandle.getLiveData("CONTACT_VIEW_MODEL_PERSONAL_EMAIL", "") }
    val webSite by lazy { stateHandle.getLiveData("CONTACT_VIEW_MODEL_WEB_SITE", "") }
    val company by lazy { stateHandle.getLiveData("CONTACT_VIEW_MODEl_COMPANY", "") }
    val companyPosition by lazy { stateHandle.getLiveData("CONTACT_VIEW_MODEL_COMPANY_POSITION", "") }
    val companyTelNumber by lazy { stateHandle.getLiveData("CONTACT_VIEW_MODEL_COMPANY_TEL_NUMBER", "") }
    val companyEmail by lazy { stateHandle.getLiveData("CONTACT_VIEW_MODEL_COMPANY_EMAIL", "") }

    fun toBarcode(): String {
        return  "BEGIN:VCARD\n" +
                "VERSION:2.1\n" +
                "FN:${firstName.value} ${lastName.value}\n" +
                "N:${lastName.value};${firstName.value}\n" +
                "TITLE:${companyPosition.value}\n" +
                "TEL;CELL;VOICE:${mobileTelNumber.value}\n" +
                "TEL;WORK;VOICE:${companyTelNumber.value}\n" +
                "TEL;HOME;VOICE:${personalTelNumber.value}\n" +
                "EMAIL;HOME;INTERNET:${personalEmail.value}\n" +
                "EMAIL;WORK;INTERNET:${companyEmail.value}\n" +
                "URL:${webSite.value}\n" +
                "ORG:${company.value}\n"
    }
}