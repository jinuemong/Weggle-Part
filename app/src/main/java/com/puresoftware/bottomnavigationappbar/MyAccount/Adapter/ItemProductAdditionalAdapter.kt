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
import com.puresoftware.bottomnavigationappbar.MyAccount.AboutReview.AddReviewActivity
import com.puresoftware.bottomnavigationappbar.R
import com.puresoftware.bottomnavigationappbar.Weggler.Model.Product
import com.puresoftware.bottomnavigationappbar.databinding.ItemMiniProductTypeAdditionalBinding
import java.text.DecimalFormat

//추가 상품 업로드 시 사용 최대 6
class ItemProductAdditionalAdapter(
    private val activity: Activity,
) : RecyclerView.Adapter<ItemProductAdditionalAdapter.ViewHolder>() {
    private lateinit var binding: ItemMiniProductTypeAdditionalBinding
    var addViewModel = (activity as AddReviewActivity).addReviewModel
    var searchData = addViewModel.searchData.value!!
    var selectData = addViewModel.selectProductData.value!!

    private var onItemClickListener : OnItemClickListener?= null
    interface OnItemClickListener{
        fun delData()
        fun addData()
    }
    fun setOnItemClickListener(listener: OnItemClickListener){
        this.onItemClickListener = listener
    }

    inner class ViewHolder(val binding: ItemMiniProductTypeAdditionalBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind() {
            val item = searchData[absoluteAdapterPosition]

            //검색 글자 변환
            val spannableString = SpannableString(item.name)
            val startIndex = spannableString.indexOf(addViewModel.searchText)

            //현재 리뷰 , community, 검색 외 데이터 제외
            if (addViewModel.reviewProduct?.productId == item.productId
                || item.name == "communityList"
                || startIndex < 0
            ) {
                binding.root.layoutParams.height = 0

            } else {
                spannableString.setSpan(
                    ForegroundColorSpan(Color.parseColor("#E60FAB")),
                    startIndex, (startIndex + addViewModel.searchText.length),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                spannableString.setSpan(
                    StyleSpan(Typeface.BOLD),
                    startIndex, (startIndex + addViewModel.searchText.length),
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
                if (addViewModel.findIsData(item) ==null) { //값 없음
                    binding.unCheckButton()
                } else { //값 있음
                    binding.isCheckButton()
                }

                //클릭 이벤트 적용
                binding.root.setOnClickListener {
                    //체크 되어 있는지 확인
                    if (addViewModel.findIsData(item)!=null) { //존재 - 삭제 후 색상 해제
                        addViewModel.delSelectData(item)
                        binding.unCheckButton()
                    } else {
                        if (selectData.size <= 6) {
                            // 최대 6개까지 셀렉
                            addViewModel.addSelectData(item)
                            binding.isCheckButton()
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

    override fun getItemCount(): Int = searchData.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(){
        searchData = addViewModel.searchData.value!!
        selectData = addViewModel.selectProductData.value!!
        Log.d("setData","..... search")
        notifyDataSetChanged()
    }
    private fun ItemMiniProductTypeAdditionalBinding.isCheckButton() = checkBox
        .setBackgroundResource(R.drawable.baseline_check_circle_24_selected)

    private fun ItemMiniProductTypeAdditionalBinding.unCheckButton() = checkBox
        .setBackgroundResource(R.drawable.baseline_check_circle_24)
}