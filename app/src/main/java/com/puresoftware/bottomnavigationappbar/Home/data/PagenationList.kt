package com.puresoftware.bottomnavigationappbar.Home.data

import com.puresoftware.bottomnavigationappbar.Weggler.Model.Page
import com.puresoftware.bottomnavigationappbar.Weggler.Model.Product
import com.puresoftware.bottomnavigationappbar.Weggler.Model.SortData
import java.io.Serializable

data class PagenationList(val content : ArrayList<GroupBuyData>,
                          val pageable : Page,
                          val totalPages : Int,
                          val totalElements : Int,
                          val number : Int,
                          val sort : SortData,
                          val size : Int,
                          val numberOfElements : Int,
                          val first : Boolean,
                          val last : Boolean,
                          val empty : Boolean
): Serializable
