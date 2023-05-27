package com.puresoftware.bottomnavigationappbar.Home.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.puresoftware.bottomnavigationappbar.Home.data.ReviewData
import com.puresoftware.bottomnavigationappbar.Home.data.ReviewInnerData
import com.puresoftware.bottomnavigationappbar.R
import com.puresoftware.bottomnavigationappbar.Weggler.Model.ReviewInCommunity

class DetailReviewAdapter(val context: Context,private val list:ArrayList<ReviewInnerData>):RecyclerView.Adapter<DetailReviewAdapter.ReviewHolder>() {
    private lateinit var exoPlayerView:PlayerView
    private lateinit var player: SimpleExoPlayer
    private var playWhenReady = true

    inner class ReviewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val myVideo = itemView.findViewById<PlayerView>(R.id.review_video)
        val profileImage = itemView.findViewById<ImageView>(R.id.profile_imageView)
        val myId = itemView.findViewById<TextView>(R.id.id)
        val myLike = itemView.findViewById<ImageView>(R.id.like)
        val likeNumber = itemView.findViewById<TextView>(R.id.like_number)
        val myChat = itemView.findViewById<ImageView>(R.id.chat)
        val chatNumber = itemView.findViewById<TextView>(R.id.chat_number)
        val myContext = itemView.findViewById<TextView>(R.id.context)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.detail_review_recyclerview_item,parent,false)
        return ReviewHolder(view)
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: ReviewHolder, position: Int) {
        exoPlayerView = holder.myVideo
            initailizePlayer(list[position].resource)

        holder.myId.text = list[position].userInfo.id
        holder.myContext.text = list[position].body.text
        holder.likeNumber.text = list[position].likeCount.toString()
//        holder.chatNumber.text = list[position].
        holder.myChat.setOnClickListener{
            Glide.with(context)
                .load(R.drawable.ic_baseline_mark_unread_chat_alt_24)
                .into(holder.myChat)
        }
        holder.myLike.setOnClickListener{
            Glide.with(context)
                .load(R.drawable.ic_baseline_favorite_24)
                .into(holder.myLike)
        }


    }

    override fun getItemCount(): Int {
        return list.size
    }
    private fun initailizePlayer(url:String){
        player = ExoPlayerFactory.newSimpleInstance(this.context)
        exoPlayerView.setPlayer(player)

        val mediaSource = buildMediaSource(Uri.parse((url)))
        player.prepare(mediaSource,true,false)
        player.setPlayWhenReady(playWhenReady)
    }
    private fun buildMediaSource(uri: Uri): MediaSource {
        val userAgent = Util.getUserAgent(this.context,"BottomNavigationAppbar")

        if(uri.lastPathSegment!!.contains("mp3")||uri.lastPathSegment!!.contains("mp4")){
            return ExtractorMediaSource.Factory(DefaultHttpDataSourceFactory(userAgent)).createMediaSource(uri)
        } else if (uri.lastPathSegment!!.contains("m3u8")){
            return HlsMediaSource.Factory(DefaultHttpDataSourceFactory(userAgent)).createMediaSource(uri)
        } else{
            return ExtractorMediaSource.Factory(DefaultDataSourceFactory(this.context,userAgent)).createMediaSource(uri)
        }
    }
}