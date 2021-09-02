package com.taetae98.qrreader.fragment

import com.taetae98.module.binding.BindingFragment
import com.taetae98.qrreader.R
import com.taetae98.qrreader.databinding.FragmentLocationBinding
import com.taetae98.qrreader.interfaces.TabComponent

class LocationFragment : BindingFragment<FragmentLocationBinding>(R.layout.fragment_location), TabComponent {
    override val tabIcon = R.drawable.ic_round_location_on_24
}