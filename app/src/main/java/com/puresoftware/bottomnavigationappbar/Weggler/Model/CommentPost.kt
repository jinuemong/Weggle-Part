package com.puresoftware.bottomnavigationappbar.Weggler.Model

import java.io.Serializable

//post comment body

class CommentPost(
    val parentCommentId : Int,
    val body : String,
) : Serializable
