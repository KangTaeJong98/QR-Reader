package com.taetae98.qrreader

import android.os.Bundle
import android.view.*
import com.taetae98.lib.binding.BindingFragment
import com.taetae98.qrreader.databinding.FragmentScanBinding

class ScanFragment : BindingFragment<FragmentScanBinding>(R.layout.fragment_scan) {
    init {
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.fragment_scan_menu, menu)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        onCreateSupportActionBar()

        return binding.root
    }

    private fun onCreateSupportActionBar() {
        setSupportActionBar(binding.toolbar)
    }
}