package com.taetae98.qrreader.fragment

import com.taetae98.lib.binding.BindingFragment
import com.taetae98.qrreader.R
import com.taetae98.qrreader.databinding.FragmentWifiBinding
import com.taetae98.qrreader.interfaces.TabComponent

class WiFiFragment : BindingFragment<FragmentWifiBinding>(R.layout.fragment_wifi), TabComponent {
    override val tabIcon = R.drawable.ic_round_wifi_24
}