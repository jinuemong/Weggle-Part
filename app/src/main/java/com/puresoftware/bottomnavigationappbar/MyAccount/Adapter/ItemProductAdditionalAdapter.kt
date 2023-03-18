package com.puresoftware.bottomnavigationappbar.MyAccount.Adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Color
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.Log
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
    private val currentProductId: Int,
) : RecyclerView.Adapter<ItemProductAdditionalAdapter.ViewHolder>() {
    private lateinit var binding: ItemMiniProductTypeAdditionalBinding
    private var dataSet = ArrayList<Product>()
    private var addProductList = ArrayList<Product>()

    var changeText = "changeText"
    private var onItemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun addItem(data: Product)
        fun delItem(data: Product)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.onItemClickListener = listener
    }

    inner class ViewHolder(val binding: ItemMiniProductTypeAdditionalBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind() {
            val item = dataSet[absoluteAdapterPosition]

            //검색 글자 변환
            val spannableString = SpannableString(item.name)
            val startIndex = spannableString.indexOf(changeText)

            //현재 리뷰 , community, 검색 외 데이터 제외
            if (currentProductId == item.productId || item.name == "communityList"
                || startIndex < 0
            ) {
                binding.root.visibility = View.GONE
                Log.d(
                    "search data test", item.productId.toString() + "," + item.name.toString() + ","
                            + startIndex.toString()
                )

            } else {
                spannableString.setSpan(
                    ForegroundColorSpan(Color.parseColor("#E60FAB")),
                    startIndex, (startIndex + changeText.length),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                spannableString.setSpan(
                    StyleSpan(Typeface.BOLD),
                    startIndex, (startIndex + changeText.length),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                binding.productName.text = spannableString

                //초기화
                Glide.with(activity)
                    .load(item.subjectFiles[0])
                    .into(binding.productImage)
                binding.productCompany.text = item.body.company
                //가격 변환 (컴마찍기) 등록
                val decimal = DecimalFormat("#,###")
                binding.price.text = decimal.format(item.body.orginal)
                binding.salePer.text = "${item.body.discount}%"
                binding.salePrice.text = decimal.format(item.body.price)

                // 재 검색 시 확인
                val checkIndex = addProductList.indexOf(item)
                if (checkIndex != -1) { //값 없음
                    binding.unCheckButton()
                } else { //값 있음
                    binding.isCheckButton()
                }

                //클릭 이벤트 적용
                binding.root.setOnClickListener {
                    //체크 되어 있는지 확인
                    if (checkIndex != -1) { //존재 - 삭제 후 색상 해제
                        addProductList.removeAt(checkIndex)
                        binding.unCheckButton()
                        onItemClickListener?.delItem(item)
                    } else {
                        if (addProductList.size <= 6) {
                            // 최대 6개까지 셀렉
                            addProductList.add(item)
                            binding.isCheckButton()
                            onItemClickListener?.addItem(item)
                        }
                    }
                }


            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemMiniProductTypeAdditionalBinding
            .inflate(LayoutInflater.from(activity), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = dataSet.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(dataList: ArrayList<Product>, newText: String) {
        dataSet = dataList
        changeText = newText
        notifyDataSetChanged()
    }

    // 선택한 리스트 반환
    fun getData(): ArrayList<Product> {
        return addProductList
    }

    //fragment로 부터 index 초기화
    @SuppressLint("NotifyDataSetChanged")
    fun setDataFromFragment(data: Product) {
        addProductList.remove(data)
        notifyDataSetChanged()
    }

    private fun ItemMiniProductTypeAdditionalBinding.isCheckButton() = checkBox
        .setBackgroundResource(R.drawable.baseline_check_circle_24_selected)

    private fun ItemMiniProductTypeAdditionalBinding.unCheckButton() = checkBox
        .setBackgroundResource(R.drawable.baseline_check_circle_24)
}