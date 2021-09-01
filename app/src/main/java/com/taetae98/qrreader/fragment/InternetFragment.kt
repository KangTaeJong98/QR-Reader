package com.taetae98.qrreader.fragment

import com.taetae98.lib.binding.BindingFragment
import com.taetae98.qrreader.R
import com.taetae98.qrreader.databinding.FragmentInternetBinding
import com.taetae98.qrreader.interfaces.TabComponent

class InternetFragment : BindingFragment<FragmentInternetBinding>(R.layout.fragment_internet), TabComponent {
    override val tabIcon = R.drawable.ic_round_public_24
}