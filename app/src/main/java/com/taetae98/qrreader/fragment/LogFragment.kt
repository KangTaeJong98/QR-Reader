package com.taetae98.qrreader.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.selection.SelectionTracker
import com.taetae98.modules.library.navigation.NavigationFragment
import com.taetae98.modules.library.toDP
import com.taetae98.modules.library.util.SpaceItemDecoration
import com.taetae98.qrreader.R
import com.taetae98.qrreader.adapter.BarcodeDataAdapter
import com.taetae98.qrreader.application.TAG
import com.taetae98.qrreader.databinding.FragmentBarcodeDataBinding
import com.taetae98.qrreader.interfaces.TabComponent
import com.taetae98.qrreader.selection.BarcodeDataSelection
import com.taetae98.qrreader.viewmodel.BarcodeDataViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LogFragment : NavigationFragment<FragmentBarcodeDataBinding>(R.layout.fragment_barcode_data), TabComponent {
    override val tabIcon = R.drawable.ic_round_history_24

    private val viewModel by activityViewModels<BarcodeDataViewModel>()
    private val selectionTracker by lazy {
        BarcodeDataSelection(binding.barcodeDataRecyclerView).apply {
            instance.addObserver(object : SelectionTracker.SelectionObserver<Long>() {
                override fun onSelectionChanged() {
                    super.onSelectionChanged()
                    Log.d(TAG, "onSelectionChanged : ${binding.isSelecting}")
                    if (binding.isSelecting != instance.hasSelection()) {
                        binding.isSelecting = instance.hasSelection()
                        CoroutineScope(Dispatchers.Main).launch {
                            barcodeDataAdapter.notifyDataSetChanged()
                        }
                    }

                    binding.isSelectOne = instance.selection.size() == 1
                }
            })
        }
    }

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

    override fun onCreateViewDataBinding() {
        super.onCreateViewDataBinding()
        binding.setOnCancel {
            selectionTracker.instance.clearSelection()
        }
        binding.setOnDelete {
            val collection = selectionTracker.instance.selection.toList()

            selectionTracker.instance.clearSelection()
            CoroutineScope(Dispatchers.IO).launch {
                viewModel.deleteByIds(collection)
            }
        }
    }

    private fun onCreateRecyclerView() {
        with(binding.barcodeDataRecyclerView) {
            adapter = barcodeDataAdapter
            addItemDecoration(SpaceItemDecoration(2.toDP()))
        }

        barcodeDataAdapter.selectionTracker = selectionTracker
    }
}