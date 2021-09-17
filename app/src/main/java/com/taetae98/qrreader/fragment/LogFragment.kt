package com.taetae98.qrreader.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.taetae98.modules.library.navigation.NavigationFragment
import com.taetae98.qrreader.R
import com.taetae98.qrreader.adapter.BarcodeDataAdapter
import com.taetae98.qrreader.databinding.FragmentLogBinding
import com.taetae98.qrreader.interfaces.TabComponent
import com.taetae98.qrreader.viewmodel.BarcodeDataViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LogFragment : NavigationFragment<FragmentLogBinding>(R.layout.fragment_log), TabComponent {
    override val tabIcon = R.drawable.ic_round_history_24

    private val viewModel by activityViewModels<BarcodeDataViewModel>()

    @Inject
    lateinit var barcodeDataAdapter: BarcodeDataAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onObserveBarcodeData()
    }

    private fun onObserveBarcodeData() {
        viewModel.barcodeData.observe(viewLifecycleOwner) {
            barcodeDataAdapter.submitList(it)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        onCreateRecyclerView()

        return binding.root
    }

    private fun onCreateRecyclerView() {
        with(binding.barcodeDataRecyclerView) {
            adapter = barcodeDataAdapter
        }
    }
}