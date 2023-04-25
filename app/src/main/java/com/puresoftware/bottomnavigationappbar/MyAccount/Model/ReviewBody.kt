package com.puresoftware.bottomnavigationappbar.MyAccount.Model

import com.puresoftware.bottomnavigationappbar.MyAccount.Adapter.AdditionalImageAdapter
import java.io.Serializable

class ReviewBody (
    val reviewText  : String,
    val additionalProduct : ArrayList<Int>?,
)