package com.puresoftware.bottomnavigationappbar.MyAccount.Model

import java.io.Serializable

class FollowData(
  val body : String?,
  var userInfo: UserInfo?,
  val createTime:String,
  val updateTime : String,
): Serializable