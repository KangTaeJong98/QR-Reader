package com.taetae98.qrreader.handler

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.CalendarContract
import android.provider.ContactsContract
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import com.google.zxing.qrcode.QRCodeReader
import com.google.zxing.qrcode.decoder.QRCodeDecoderMetaData
import com.taetae98.qrreader.R
import com.taetae98.qrreader.application.TAG
import java.util.*
import kotlin.collections.ArrayList

abstract class BarcodeActionHandler {
    fun action(barcode: String) {
        try {
            Log.d(TAG, "Action Barcode : $barcode")

            val scheme = barcode.substringBefore(":").trim()
            when {
                scheme.equals("https", true) || scheme.equals("http", true) -> {
                    onInternet(barcode)
                }
                scheme.equals("wifi", true) -> {
                    onWiFi(barcode)
                }
                scheme.equals("geo", true) -> {
                    onLocation(barcode)
                }
                scheme.equals("tel", true) -> {
                    onTel(barcode)
                }
                scheme.equals("smsto", true) -> {
                    onMessage(barcode)
                }
                scheme.equals("begin", true) -> {
                    val query = barcode.substringBefore("\n").substringAfter(":").trim()
                    Log.d(TAG, "Query : $query")
                    when {
                        query.equals("VCARD", true) -> {
                            onContact(barcode)
                        }
                        query.equals("VEVENT", true) -> {
                            onCalendar(barcode)
                        }
                    }
                }
                scheme.equals("matmsg", true) -> {
                    onEmail(barcode)
                }
                else -> {
                    onNothing(barcode)
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Action Error : ", e)
            onNothing(barcode)
        }
    }

    protected abstract fun onNothing(barcode: String)
    protected abstract fun onInternet(barcode: String)
    protected abstract fun onWiFi(barcode: String)
    protected abstract fun onLocation(barcode: String)
    protected abstract fun onTel(barcode: String)
    protected abstract fun onMessage(barcode: String)
    protected abstract fun onContact(barcode: String)
    protected abstract fun onCalendar(barcode: String)
    protected abstract fun onEmail(barcode: String)

    open class SimpleBarcodeActionHandler(
        private val context: Context
    ) : BarcodeActionHandler() {
        override fun onNothing(barcode: String) {
            Toast.makeText(context, barcode, Toast.LENGTH_SHORT).show()
        }

        override fun onInternet(barcode: String) {
            context.startActivity(Intent.createChooser(
                Intent(Intent.ACTION_VIEW, Uri.parse(barcode)),
                context.getString(R.string.internet)
            ))
        }

        override fun onWiFi(barcode: String) {
            context.startActivity(Intent.createChooser(
                Intent(Settings.ACTION_WIFI_SETTINGS),
                context.getString(R.string.wifi)
            ))
        }

        override fun onLocation(barcode: String) {
            context.startActivity(Intent.createChooser(
                Intent(Intent.ACTION_VIEW, Uri.parse(barcode)),
                context.getString(R.string.location)
            ))
        }

        override fun onTel(barcode: String) {
            context.startActivity(Intent.createChooser(
                Intent(Intent.ACTION_DIAL, Uri.parse(barcode)),
                context.getString(R.string.tel)
            ))
        }

        override fun onMessage(barcode: String) {
            val uri = barcode.substringBeforeLast(":")
            val message = barcode.substringAfterLast(":")
            context.startActivity(Intent.createChooser(
                Intent(Intent.ACTION_SEND, Uri.parse(uri)).apply {
                    putExtra("sms_body", message)
                },
                context.getString(R.string.message)
            ))
        }

        override fun onContact(barcode: String) {
            val data = ContactData()

            barcode.split("\n").forEach { stream ->
                when(stream.substringBefore(":").substringBefore(";")) {
                    "FN" -> {
                        stream.substringAfter(":").split(" ").also {
                            data.firstName = it.getOrNull(0)
                            data.lastName = it.getOrNull(1)
                        }
                    }
                    "N" -> {
                        stream.substringAfter(":").split(";").also {
                            data.firstName = it.getOrNull(1)
                            data.lastName = it.getOrNull(0)
                        }
                    }
                    "TITLE" -> {
                        data.companyPosition = stream.substringAfter(":")
                    }
                    "TEL" -> {
                        when(stream.substringAfter(";").substringBefore(";")) {
                            "HOME" -> {
                                data.personalTelNumber = stream.substringAfterLast(":")
                            }
                            "CELL" -> {
                                data.mobileTelNumber = stream.substringAfterLast(":")
                            }
                            "WORK" -> {
                                data.companyTelNumber = stream.substringAfterLast(":")
                            }
                        }
                    }
                    "EMAIL" -> {
                        when(stream.substringAfter(";").substringBefore(";")) {
                            "HOME" -> {
                                data.personalEmail = stream.substringAfterLast(":")
                            }
                            "WORK" -> {
                                data.companyEmail = stream.substringAfterLast(":")
                            }
                        }
                    }
                    "URL" -> {
                        data.webSite = stream.substringAfterLast(":")
                    }
                    "ORG" -> {
                        data.company = stream.substringAfterLast(":")
                    }
                }
            }

            val extras = ArrayList<ContentValues>().apply {
                add(ContentValues().apply {
                    put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
                    put(ContactsContract.CommonDataKinds.Email.TYPE, ContactsContract.CommonDataKinds.Email.TYPE_HOME)
                    put(ContactsContract.CommonDataKinds.Email.ADDRESS, data.personalEmail)
                })
                add(ContentValues().apply {
                    put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
                    put(ContactsContract.CommonDataKinds.Email.TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK)
                    put(ContactsContract.CommonDataKinds.Email.ADDRESS, data.companyEmail)
                })

                add(ContentValues().apply {
                    put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                    put(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                    put(ContactsContract.CommonDataKinds.Phone.NUMBER, data.mobileTelNumber)
                })
                add(ContentValues().apply {
                    put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                    put(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_HOME)
                    put(ContactsContract.CommonDataKinds.Phone.NUMBER, data.personalTelNumber)
                })
                add(ContentValues().apply {
                    put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                    put(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_WORK)
                    put(ContactsContract.CommonDataKinds.Phone.NUMBER, data.companyTelNumber)
                })

                add(ContentValues().apply {
                    put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE)
                    put(ContactsContract.CommonDataKinds.Website.TYPE, ContactsContract.CommonDataKinds.Website.TYPE_HOMEPAGE)
                    put(ContactsContract.CommonDataKinds.Website.DATA, data.webSite)
                })
            }

            context.startActivity(Intent.createChooser(
                Intent(Intent.ACTION_INSERT, ContactsContract.Contacts.CONTENT_URI).apply {
                    putExtra(ContactsContract.Intents.Insert.NAME, "${data.firstName} ${data.lastName}")
                    putExtra(ContactsContract.Intents.Insert.COMPANY, data.company)
                    putExtra(ContactsContract.Intents.Insert.JOB_TITLE, data.companyPosition)
                    putParcelableArrayListExtra(ContactsContract.Intents.Insert.DATA, extras)
                },
                context.getString(R.string.contact)
            ))
        }

        override fun onCalendar(barcode: String) {
            val data = CalendarData()

            val toMillis = lambda@{ date: String, isAllDay: Boolean ->
                val calendar = GregorianCalendar()
                val year = try { date.substring(0..3).toInt() } catch (e: Exception) { calendar[Calendar.YEAR] }
                val month = try { date.substring(4..5).toInt() } catch (e: Exception) { calendar[Calendar.MONTH]}
                val dayOfMonth = try { date.substring(6..7).toInt() } catch (e: Exception) { calendar[Calendar.DAY_OF_MONTH]}
                val hour = try { date.substring(9..10).toInt() } catch (e: Exception) { calendar[Calendar.HOUR_OF_DAY]}
                val minute = try { date.substring(11..12).toInt() } catch (e: Exception) { calendar[Calendar.MINUTE] }
                val second = try { date.substring(13..14).toInt() } catch (e: Exception) { calendar[Calendar.SECOND]}

                return@lambda if (isAllDay) {
                    GregorianCalendar(year, month - 1, dayOfMonth).timeInMillis
                } else {
                    GregorianCalendar(year, month - 1, dayOfMonth, hour, minute, second).timeInMillis
                }
            }

            barcode.split("\n").forEach { stream ->
                when(stream.substringBefore(":").substringBefore(";")) {
                    "SUMMARY" -> {
                        data.title = stream.substringAfter(":")
                    }
                    "DTSTART" -> {
                        data.allDay = stream.contains("VALUE=DATE")
                        val date = stream.substringAfterLast(":").substringAfterLast(";").substringAfterLast("VALUE=")
                        data.begin = toMillis(date, data.allDay!!)
                    }
                    "DTEND" -> {
                        data.allDay = stream.contains("VALUE=DATE")
                        val date = stream.substringAfterLast(":").substringAfterLast(";").substringAfterLast("VALUE=")
                        data.end = toMillis(date, data.allDay!!)
                    }
                    "LOCATION" -> {
                        data.location = stream.substringAfter(":")
                    }
                    "DESCRIPTION" -> {
                        data.description = stream.substringAfter(":")
                    }
                }
            }

            context.startActivity(Intent.createChooser(
                Intent(Intent.ACTION_INSERT, CalendarContract.Events.CONTENT_URI).apply {
                    putExtra(CalendarContract.Events.TITLE, data.title)
                    putExtra(CalendarContract.Events.DESCRIPTION, data.description)
                    putExtra(CalendarContract.Events.EVENT_LOCATION, data.location)
                    putExtra(CalendarContract.Events.ALL_DAY, data.allDay)
                    putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, data.begin)
                    putExtra(CalendarContract.EXTRA_EVENT_END_TIME, data.end)
                },
                context.getString(R.string.calendar)
            ))
        }

        override fun onEmail(barcode: String) {
            val email = barcode.substringAfter("TO:").substringBefore(";")
            val title = barcode.substringAfter("SUB:").substringBefore(";")
            val message = barcode.substringAfter("BODY:").substringBeforeLast(";").substringBeforeLast(";")

            context.startActivity(Intent.createChooser(
                Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:")).apply {
                    putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
                    putExtra(Intent.EXTRA_SUBJECT, title)
                    putExtra(Intent.EXTRA_TEXT, message)
                },
                context.getString(R.string.email)
            ))
        }
    }

    data class ContactData(
        var firstName: String? = null,
        var lastName: String? = null,
        var mobileTelNumber: String? = null,
        var personalTelNumber: String? = null,
        var personalEmail: String? = null,
        var webSite: String? = null,
        var company: String? = null,
        var companyPosition: String? = null,
        var companyTelNumber: String? = null,
        var companyEmail: String? = null
    )

    data class CalendarData(
        var title: String? = null,
        var begin: Long? = null,
        var end: Long? = null,
        var location: String? = null,
        var description: String? = null,
        var allDay: Boolean? = null
    )
}