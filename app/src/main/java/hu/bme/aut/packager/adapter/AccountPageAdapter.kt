package hu.bme.aut.packager.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import hu.bme.aut.packager.userAccount.fragments.AccountDetailsFragment
import hu.bme.aut.packager.userAccount.fragments.UndertakenPackagesFragment

class AccountPageAdapter(val fm: FragmentManager, val context: Context) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    companion object{
        const val NUM_PAGES = 2
    }


    override fun getCount(): Int = NUM_PAGES

    override fun getItem(position: Int): Fragment {

        return when(position){
            0 -> AccountDetailsFragment(fm)
            1 -> UndertakenPackagesFragment(context)
            else -> AccountDetailsFragment(fm)
        }
    }
}