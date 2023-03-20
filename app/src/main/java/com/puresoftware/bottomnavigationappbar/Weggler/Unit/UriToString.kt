package com.puresoftware.bottomnavigationappbar.Weggler.Unit

import android.annotation.SuppressLint
import android.app.Activity
import android.net.Uri
import android.provider.MediaStore
import android.util.Log

//Uri를 String으로 전환
@SuppressLint("Recycle")
fun getImageFilePath(activity:Activity, contentUri:Uri):String{
    var columnIndex = 0
    val projection = arrayOf(MediaStore.Images.Media.DATA)

    val cursor = activity.contentResolver.query(contentUri,projection,
        null,null,null)
    if (cursor!!.moveToFirst()){
        columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
    }
    return cursor.getString(columnIndex)
}


@SuppressLint("Recycle")
fun getVideoFilePath(activity:Activity, contentUri:Uri):String{
    var columnIndex = 0
    val projection = arrayOf(MediaStore.Video.Media.DATA)

    val cursor = activity.contentResolver.query(contentUri,projection,
        null,null,null)
    if (cursor!!.moveToFirst()){
        columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)
    }
    Log.d("test in getVideo 1",columnIndex.toString())
    Log.d("test in getVideo 2",cursor.getString(columnIndex).toString())

    return cursor.getString(columnIndex)
}