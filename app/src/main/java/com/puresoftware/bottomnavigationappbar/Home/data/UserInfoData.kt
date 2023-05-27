package com.puresoftware.bottomnavigationappbar.Home.data

import java.io.Serializable

data class UserInfoData(val id:String,
                        var profileFile:String = "",
                        var backgroundFile:String = ""): Serializable
