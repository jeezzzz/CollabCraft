package com.jeezzzz.collabcraft

data class Users(
    val name: String = "",
    val collegeEmail: String = "",
    val year: String = "",
    val branch: String = "",
    val domain1: String = "",
    val domain2: String = "",
    val domain3: String = "",
    var posts: ArrayList<Post> = ArrayList()
)
