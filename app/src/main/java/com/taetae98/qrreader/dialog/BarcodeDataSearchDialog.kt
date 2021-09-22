package com.taetae98.qrreader.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.taetae98.modules.library.navigation.NavigationDialogFragment
import com.taetae98.modules.library.toDP
import com.taetae98.modules.library.util.SpaceItemDecoration
import com.taetae98.qrreader.R
import com.taetae98.qrreader.adapter.BarcodeDataAdapter
import com.taetae98.qrreader.databinding.DialogBarcodeDataSearchBinding
import com.taetae98.qrreader.viewmodel.BarcodeDataViewModel
import com.taetae98.qrreader.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BarcodeDataSearchDialog : NavigationDialogFragment<DialogBarcodeDataSearchBinding>(R.layout.dialog_barcode_data_search) {
    private val searchViewModel by viewModels<SearchViewModel>()
    private val barcodeDataViewModel by activityViewModels<BarcodeDataViewModel>()

    @Inject
    lateinit var barcodeDataAdapter: BarcodeDataAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)

        onObserveSearch()
    }

    private fun onObserveSearch() {
        searchViewModel.search.observe(viewLifecycleOwner) { query ->
            barcodeDataViewModel.barcodeData.value?.filter {
                it.name.contains(query) || it.barcode.contains(query)
            }?.also {
                barcodeDataAdapter.submitList(it)
            }
        }
    }

    override fun onCreateViewDataBinding() {
        super.onCreateViewDataBinding()
        binding.searchViewModel = searchViewModel
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        onCreateRecyclerView()

        return binding.root
    }

    private fun onCreateRecyclerView() {
        with(binding.barcodeDataRecyclerView) {
            adapter = barcodeDataAdapter
            addItemDecoration(SpaceItemDecoration(5.toDP()))
        }
    }
}