package kr.co.ky.weggle

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class FragmentAdapter(fa:FragmentActivity):FragmentStateAdapter(fa) {
    var fragments : ArrayList<Fragment> = ArrayList()
    override fun getItemCount()=fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]

    fun addFragment(fragment: Fragment){
        fragments.add(fragment)
        notifyItemInserted(fragments.size-1)
    }
}