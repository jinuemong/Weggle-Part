package com.puresoftware.bottomnavigationappbar.MyAccount.Model

import java.io.Serializable

class UserInfo(
    val id : String, //user name
    val profileFile : String = "",
    val backgroundFile : String = "",
): Serializable