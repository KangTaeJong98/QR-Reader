package com.taetae98.qrreader.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.taetae98.module.binding.BindingFragment
import com.taetae98.qrreader.R
import com.taetae98.qrreader.databinding.FragmentGenerateBinding
import com.taetae98.qrreader.interfaces.TabComponent

class GenerateFragment : BindingFragment<FragmentGenerateBinding>(R.layout.fragment_generate) {
    private val viewPagerAdapter by lazy {
        object : FragmentStateAdapter(this) {
            private val array by lazy {
                arrayOf<Fragment>(
                    CodeFragment(),
                    InternetFragment(), WiFiFragment(), LocationFragment(),
                    TelFragment(), MessageFragment(), ContactFragment(),
                    CalendarFragment(), EmailFragment()
                )
            }

            override fun getItemCount(): Int {
                return array.size
            }

            override fun createFragment(position: Int): Fragment {
                return array[position]
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        onCreateViewPager()
        onCreateTabLayoutMediator()

        return binding.root
    }

    private fun onCreateViewPager() {
        binding.viewPager.adapter = viewPagerAdapter
    }

    private fun onCreateTabLayoutMediator() {
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, i ->
            val fragment = viewPagerAdapter.createFragment(i)
            if (fragment is TabComponent) {
                tab.setIcon(fragment.tabIcon)
            }
        }.attach()
    }
}