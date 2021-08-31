package com.taetae98.lib.base

import androidx.fragment.app.Fragment

class BaseFragment : Fragment() {
    fun finish() {
        requireActivity().finish()
    }
}