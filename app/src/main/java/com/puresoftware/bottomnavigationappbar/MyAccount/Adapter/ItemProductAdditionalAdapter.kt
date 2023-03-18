package com.puresoftware.bottomnavigationappbar.MyAccount.Adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.puresoftware.bottomnavigationappbar.R
import com.puresoftware.bottomnavigationappbar.Weggler.Model.Product
import com.puresoftware.bottomnavigationappbar.databinding.ItemMiniProductTypeAdditionalBinding
import java.text.DecimalFormat

//추가 상품 업로드 시 사용 최대 6
class ItemProductAdditionalAdapter(
    private val activity: Activity,
    private val currentProductId :Int,
):RecyclerView.Adapter<ItemProductAdditionalAdapter.ViewHolder>() {
    private lateinit var binding : ItemMiniProductTypeAdditionalBinding
    private var dataSet = ArrayList<Product>()
    private var addProductList = ArrayList<Int>()

    private var onItemClickListener : OnItemClickListener? = null
    interface OnItemClickListener{
        fun addItem(imageUrl : String)
        fun delItem(checkIndex : Int)
    }
    fun setOnItemClickListener(listener: OnItemClickListener){
        this.onItemClickListener = listener
    }

    inner class ViewHolder(val binding: ItemMiniProductTypeAdditionalBinding)
        :RecyclerView.ViewHolder(binding.root){
            @SuppressLint("SetTextI18n")
            fun bind(){
                val item = dataSet[absoluteAdapterPosition]

                //현재 리뷰 제외
                if (currentProductId==item.productId){
                    binding.root.visibility = View.GONE
                }

                //초기화
                Glide.with(activity)
                    .load(item.subjectFiles[0])
                    .into(binding.productImage)
                binding.productCompany.text = item.body.company
                binding.productName.text = item.name
                //가격 변환 (컴마찍기) 등록
                val decimal = DecimalFormat("#,###")
                binding.price.text= decimal.format(item.body.orginal)
                binding.salePer.text = "${item.body.discount}%"
                binding.salePrice.text = decimal.format(item.body.price)

                //클릭 이벤트 적용
                binding.root.setOnClickListener {
                    //체크 되어 있는지 확인
                    val checkIndex = addProductList.indexOf(item.productId)
                    if (checkIndex!=-1){ //존재 - 삭제 후 색상 해제
                        addProductList.removeAt(checkIndex)
                        unCheckButton()
                        onItemClickListener?.delItem(checkIndex)
                    }else {
                        if (addProductList.size <= 6) {
                            // 최대 6개까지 셀렉
                            addProductList.add(item.productId)
                            isCheckButton()
                            onItemClickListener?.addItem(item.subjectFiles[0])
                        }
                    }
                }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemMiniProductTypeAdditionalBinding
            .inflate(LayoutInflater.from(activity),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = dataSet.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(dataList : ArrayList<Product>){
        dataSet = dataList
        notifyDataSetChanged()
    }

    // 선택한 리스트 반환
    fun getData() : ArrayList<Int>{
        return addProductList
    }

    //fragment로 부터 index 초기화
    fun setDataFromFragment(index : Int){
        val dataIndex = dataSet.indexOf(dataSet.find { it.productId==addProductList[index]})
        addProductList.removeAt(index)
        notifyItemChanged(dataIndex)
    }

    private fun isCheckButton()  = binding.checkBox
        .setBackgroundResource(R.drawable.baseline_check_circle_24_selected)
    private fun unCheckButton() = binding.checkBox
        .setBackgroundResource(R.drawable.baseline_check_circle_24)
}