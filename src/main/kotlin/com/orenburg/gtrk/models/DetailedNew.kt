package com.orenburg.gtrk.models

data class DetailedNew(
    val id: Int = 0,
    val type : NewsType = NewsType.All,
    val header: String = "default",
    val date: String = "default",
    val urlImage: String? = "default",
    val article : String = "default",
    val urlVideo: String? = "default"
)
