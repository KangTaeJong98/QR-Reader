package com.taetae98.qrreader.fragment

import com.taetae98.lib.binding.BindingFragment
import com.taetae98.qrreader.R
import com.taetae98.qrreader.databinding.FragmentMessageBinding
import com.taetae98.qrreader.interfaces.TabComponent

class MessageFragment : BindingFragment<FragmentMessageBinding>(R.layout.fragment_message), TabComponent {
    override val tabIcon = R.drawable.ic_round_message_24
}