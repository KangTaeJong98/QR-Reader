package com.taetae98.qrreader.fragment

import com.taetae98.modules.library.navigation.NavigationFragment
import com.taetae98.qrreader.R
import com.taetae98.qrreader.databinding.FragmentLogBinding
import com.taetae98.qrreader.interfaces.TabComponent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LogFragment : NavigationFragment<FragmentLogBinding>(R.layout.fragment_log), TabComponent {
    override val tabIcon = R.drawable.ic_round_history_24
}