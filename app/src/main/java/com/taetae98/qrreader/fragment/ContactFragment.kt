package com.taetae98.qrreader.fragment

import com.taetae98.lib.binding.BindingFragment
import com.taetae98.qrreader.R
import com.taetae98.qrreader.databinding.FragmentContactBinding
import com.taetae98.qrreader.interfaces.TabComponent

class ContactFragment : BindingFragment<FragmentContactBinding>(R.layout.fragment_contact), TabComponent {
    override val tabIcon = R.drawable.ic_round_perm_contact_calendar_24
}