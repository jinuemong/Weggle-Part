package com.puresoftware.bottomnavigationappbar.MyAccount.Unit

import android.app.Activity
import android.media.MediaMetadataRetriever
import android.net.Uri


fun getVideoTime(activity:Activity, videoUrl: Uri?): Long{
    if (videoUrl!=null) {
        val retriever = MediaMetadataRetriever()
        retriever.setDataSource(activity,videoUrl)
        val time  = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
        val duration = time!!.toLong()/1000
        val hours = duration/3600
        val minutes = (duration -hours*3600)/60
        val seconds = duration - (hours * 3600 + minutes*60)
        return seconds
    }
    return 0
}