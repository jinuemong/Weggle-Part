package com.puresoftware.bottomnavigationappbar.MyAccount.Manager

import android.app.Activity
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import com.puresoftware.bottomnavigationappbar.MyAccount.Model.ReviewData
import com.puresoftware.bottomnavigationappbar.MyAccount.ViewModel.AddReviewViewModel
import com.puresoftware.bottomnavigationappbar.Server.MasterApplication
import com.puresoftware.bottomnavigationappbar.Weggler.Model.MultiCommunityDataBody
import com.puresoftware.bottomnavigationappbar.Weggler.Model.ReviewInCommunity

class ReviewManager(
    private val masterApp: MasterApplication,
    private val addReviewModel: AddReviewViewModel
) {
    //MultiPart
    @RequiresApi(Build.VERSION_CODES.Q)
    fun addReviewData(reviewText:String, file: Uri?, activity: Activity,
                      paramFunc: (ReviewData?, String?) -> Unit){
        addReviewModel.uploadReviewPoster(reviewText,file,activity,
            paramFunc = { reviewData, errorMessage ->
                if (reviewData!=null){
                    paramFunc(reviewData,null)
                }else{
                    paramFunc(null,errorMessage)
                }
        })
    }
}