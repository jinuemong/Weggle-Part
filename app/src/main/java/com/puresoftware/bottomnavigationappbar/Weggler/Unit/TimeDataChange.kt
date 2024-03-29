package com.puresoftware.bottomnavigationappbar.Weggler.Unit

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun getTimeText(createTime: String): String {
    val uploadTimeStamp = createTime.split("-", "T", ":", ".")
    val timeData = uploadTimeStamp[0] + "-" + uploadTimeStamp[1] + "-" + uploadTimeStamp[2] +
            " " + uploadTimeStamp[3] + ":" + uploadTimeStamp[4] + ":" + uploadTimeStamp[5].chunked(2)[0]
    val timeString = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(timeData)?.time
    val today = Calendar.getInstance().time.time
    return if (timeString != null) {
        val calDate = (today - timeString)
        if ((calDate / (60 * 60 * 24 * 1000)) >= 365){ //일 년 이상
            ((calDate / (60 * 60 * 24 * 1000)) / 365).toInt().toString() + " 년 전"
        }
        else if ((calDate / (60 * 60 * 24 * 1000)) >= 31){ //한 달 이상
            ((calDate / (60 * 60 * 24 * 1000)) / 31).toInt().toString() + " 개월 전"
        }
        else if ((calDate / (60 * 60 * 24 * 1000)) >= 1) { //1일 이상
            (calDate / (60 * 60 * 24 * 1000)).toInt().toString() + " 일 전"
        } else if ((calDate / (60 * 60 * 1000)) >= 1) { //1시간 이상
            (calDate / (60 * 60 * 1000)).toInt().toString() + " 시간 전"
        } else if ((calDate / (60 * 1000)) >= 1) { //분 단위
            (calDate / (60 * 1000)).toInt().toString() + " 분 전"
        } else {
            "몇초 전"
        }
    } else { "" }
}