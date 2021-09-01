package com.taetae98.qrreader.fragment

import com.taetae98.lib.binding.BindingFragment
import com.taetae98.qrreader.R
import com.taetae98.qrreader.databinding.FragmentCalendarBinding
import com.taetae98.qrreader.interfaces.TabComponent

class CalendarFragment : BindingFragment<FragmentCalendarBinding>(R.layout.fragment_calendar), TabComponent {
    override val tabIcon = R.drawable.ic_round_calendar_today_24
}