package com.example.examcountdown.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class SectionsPagerAdapter(private val context: Context, fm: FragmentManager)
    : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
            var fragment: Fragment? = null
            if (position == 0) {
                fragment = FutureFragment()
            } else if (position == 1) {
                fragment = PastFragment()
            }
            return fragment!!
        }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        var title: String? = null
        if (position == 0) {
            title = "Future"
        } else if (position == 1) {
            title = "Past"
        }
        return title
    }
}