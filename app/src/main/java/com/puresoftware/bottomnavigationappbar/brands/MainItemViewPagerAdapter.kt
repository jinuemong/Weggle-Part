package com.puresoftware.bottomnavigationappbar.brands

import android.content.Context
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.puresoftware.bottomnavigationappbar.R
import com.puresoftware.bottomnavigationappbar.databinding.MainItemViewpagerBinding

class MainItemViewPagerAdapter(private val listData: ArrayList<MainViewPagerData>) :
    RecyclerView.Adapter<ViewHolderPage>() {

    var context: Context? = null

    lateinit var binding: MainItemViewpagerBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderPage {
        context = parent.context // context 가져오기
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.main_item_viewpager,
            parent,
            false
        )
        return ViewHolderPage(binding)
    }

    override fun onBindViewHolder(holder: ViewHolderPage, position: Int) {
        val viewHolder: ViewHolderPage = holder
        viewHolder.onBind(listData[position % listData.size])

        Log.i("asdf","position ${position % listData.size}")
    }

    override fun getItemCount(): Int = Int.MAX_VALUE
}

class ViewHolderPage(val binding: MainItemViewpagerBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(data: MainViewPagerData) {
        Glide.with(binding.root.context).load(data.url).into(binding.ivBrandsMainItem)
    }
}