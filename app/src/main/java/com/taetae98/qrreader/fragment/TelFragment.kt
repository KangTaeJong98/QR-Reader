package com.taetae98.qrreader.fragment

import com.taetae98.lib.binding.BindingFragment
import com.taetae98.qrreader.R
import com.taetae98.qrreader.databinding.FragmentTelBinding
import com.taetae98.qrreader.interfaces.TabComponent

class TelFragment : BindingFragment<FragmentTelBinding>(R.layout.fragment_tel), TabComponent {
    override val tabIcon = R.drawable.ic_round_call_24
}