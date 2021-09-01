package com.taetae98.qrreader.fragment

import com.taetae98.lib.binding.BindingFragment
import com.taetae98.qrreader.R
import com.taetae98.qrreader.databinding.FragmentEmailBinding
import com.taetae98.qrreader.interfaces.TabComponent

class EmailFragment : BindingFragment<FragmentEmailBinding>(R.layout.fragment_email), TabComponent {
    override val tabIcon = R.drawable.ic_round_email_24
}