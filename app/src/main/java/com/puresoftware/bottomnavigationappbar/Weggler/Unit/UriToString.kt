package com.puresoftware.bottomnavigationappbar.Weggler.Unit

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentResolver
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.ParcelFileDescriptor
import android.provider.DocumentsContract
import android.provider.DocumentsContract.Document
import android.provider.MediaStore
import android.provider.MediaStore.Video.Media
import android.provider.OpenableColumns
import android.util.Log
import androidx.annotation.RequiresApi
import com.bumptech.glide.load.model.ResourceLoader.UriFactory
import java.io.FileDescriptor

//Uri를 String으로 전환
@SuppressLint("Recycle")
fun getImageFilePath(activity: Activity, contentUri: Uri): String {
    var columnIndex = 0
    val projection = arrayOf(MediaStore.Images.Media.DATA)

    val cursor = activity.contentResolver.query(
        contentUri, projection,
        null, null, null
    )
    if (cursor!!.moveToFirst()) {
        columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
    }
    return cursor.getString(columnIndex)
}

@RequiresApi(Build.VERSION_CODES.Q)
@SuppressLint("Recycle", "Range")
fun getVideoFilePath(activity: Activity, contentUri: Uri): String{
    val cursor  = activity.contentResolver.query(contentUri,
    null,null,null)

    cursor?.use {
        if (it.moveToFirst()){ // 첫 발견
            val displayName :String = it.getString(it
                .getColumnIndex(OpenableColumns.DISPLAY_NAME))
            val projection = arrayOf(Media.DATA)
            val newCursor = activity.contentResolver.query(Media.EXTERNAL_CONTENT_URI
            ,projection,null,null,null)
            newCursor?.use {it2->
                if (it2.moveToFirst()){
                    val data = it2.getString(it2.getColumnIndex(Media.DATA))
                    if (data.contains(displayName)){
                        return data
                    }
                }
            }
        }
    }

    return "null"
}
