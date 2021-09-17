package com.taetae98.qrreader.fragment

import com.taetae98.modules.library.navigation.NavigationFragment
import com.taetae98.qrreader.R
import com.taetae98.qrreader.databinding.FragmentBookmarkBinding
import com.taetae98.qrreader.interfaces.TabComponent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookmarkFragment : NavigationFragment<FragmentBookmarkBinding>(R.layout.fragment_bookmark), TabComponent {
    override val tabIcon = R.drawable.ic_round_star_24
}