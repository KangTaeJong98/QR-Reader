package com.taetae98.qrreader.fragment

import com.taetae98.lib.binding.BindingFragment
import com.taetae98.qrreader.R
import com.taetae98.qrreader.databinding.FragmentTextBinding
import com.taetae98.qrreader.interfaces.TabComponent

class TextFragment : BindingFragment<FragmentTextBinding>(R.layout.fragment_text), TabComponent {
    override val tabIcon = R.drawable.ic_round_text_fields_24
}