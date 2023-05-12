package com.puresoftware.bottomnavigationappbar.Home.data

import com.puresoftware.bottomnavigationappbar.Weggler.Model.Product

data class GroupBuyList(val name:String,
                        val productList:ArrayList<GroupBuyData>,
                        val createTime:String,
                        val updateTime:String)
