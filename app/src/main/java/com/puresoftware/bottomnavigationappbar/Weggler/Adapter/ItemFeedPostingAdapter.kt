package com.puresoftware.bottomnavigationappbar.Weggler.Adapter

import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.puresoftware.bottomnavigationappbar.MainActivity
import com.puresoftware.bottomnavigationappbar.MyAccount.Adapter.ItemProductHoAdapter
import com.puresoftware.bottomnavigationappbar.MyAccount.Model.ReviewData
import com.puresoftware.bottomnavigationappbar.Weggler.Model.Product
import com.puresoftware.bottomnavigationappbar.Weggler.Unit.getTimeText
import com.puresoftware.bottomnavigationappbar.databinding.ItemReviewBinding

//리뷰 데이터 어댑터

class ItemFeedPostingAdapter(
    private val mainActivity: MainActivity,
    dataList : ArrayList<ReviewData>
): RecyclerView.Adapter<ItemFeedPostingAdapter.ItemFeedPostingViewHolder>(){
    private lateinit var binding : ItemReviewBinding
    var dataSet = dataList

    private var onItemClickListener: OnItemClickListener? = null
    interface OnItemClickListener{
        fun setProductData(productList : ArrayList<Int>,paramFunc : ( ArrayList<Product>)->Unit)
    }

    fun setOnItemClickListener(listener: OnItemClickListener){
        this.onItemClickListener = listener
    }
    inner class ItemFeedPostingViewHolder(private val binding : ItemReviewBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val posting = dataSet[absoluteAdapterPosition]

            //뷰 세팅
            posting.userInfo?.let {
                Glide.with(mainActivity)
                    .load(it.profileFile)
                    .into(binding.userImage)
                binding.userName.text = it.id
            }

            binding.createTime.text = getTimeText(posting.createTime)

            binding.videoView.apply {
                layoutParams = binding.videoContainer.layoutParams
                getThumbnail(posting.thumbnail,this)
                setVideoPath(posting.resource)
                setMediaController(null)
                setOnClickListener {
                    setBackgroundResource(0)
                    if(isPlaying){
                        binding.playButton.visibility = View.VISIBLE
                        pause()
                    }else{
                        binding.playButton.visibility = View.GONE
                        start()
                    }
                }
                setOnCompletionListener {
                    binding.playButton.visibility = View.VISIBLE
                }
            }

            binding.likeNum.text = posting.likeCount.toString()
            binding.commentNum.text = posting.commentCount.toString()
            binding.bodyText.text = posting.body.reviewText

            //프로덕트 리스트
            var productList = arrayListOf<Int>()
            if (posting.body.additionalProduct!=null) productList = posting.body.additionalProduct!!
            productList.add(0,posting.productId)

            // 프로덕트 리스트 불러오기
            onItemClickListener?.setProductData(productList, paramFunc = {dataSet->
                binding.productListView.adapter = ItemProductHoAdapter(mainActivity,dataSet)
            })

            //클릭 리스너

            //댓글 클릭
            binding.commentImage.setOnClickListener {

            }
            binding.commentNum.setOnClickListener {

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemFeedPostingViewHolder {
        binding = ItemReviewBinding.inflate(LayoutInflater.from(mainActivity),parent,false)
        return ItemFeedPostingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemFeedPostingViewHolder, position: Int) {
        holder.bind()
    }
    private fun getThumbnail(path: String,layout:VideoView) {
        Glide.with(mainActivity)
            .load(path)
            .into(object : CustomTarget<Drawable>() {
                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable>?
                ) {

                    layout.background = resource
                }

                override fun onLoadCleared(placeholder: Drawable?) {}
            })
    }

    override fun getItemCount(): Int = dataSet.size
}