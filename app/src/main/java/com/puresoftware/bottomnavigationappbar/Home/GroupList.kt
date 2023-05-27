package com.puresoftware.bottomnavigationappbar.Home

import com.puresoftware.bottomnavigationappbar.Home.data.GroupBuyData
import com.puresoftware.bottomnavigationappbar.Home.data.GroupBuyList
import com.puresoftware.bottomnavigationappbar.Weggler.Model.Product

data class GroupList(
    val name:String,
    val productList:ArrayList<GroupBuyData>,
    val thumbnail:String,
    val resource:String,
    val createTime:String,
    val updateTime:String
)
