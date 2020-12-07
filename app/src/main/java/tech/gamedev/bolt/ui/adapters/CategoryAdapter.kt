package tech.gamedev.bolt.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import tech.gamedev.bolt.ui.categorytabs.*

class CategoryAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    override fun getCount(): Int {
        return 5
    }

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> {
                return PopularFragment()
            }
            1 -> {
                return ComputersFragment()
            }
            2 -> {
                return ElectronicsFragment()
            }
            3 -> {
                return GamingFragment()
            }
            4 -> {
                return FashionFragment()
            }
            else -> {
                return PopularFragment()
            }
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {

        when (position) {
            0 -> {
                return "Popular"
            }
            1 -> {
                return "Computers"
            }
            2 -> {
                return "Electronics"
            }
            3 -> {
                return "Gaming"
            }
            4 -> {
                return "Fashion"
            }
        }
        return super.getPageTitle(position)
    }
}