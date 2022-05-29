package com.orenburg.gtrk.models

data class DetailedNew(
    val id: Int = 0,
    val type : NewsType = NewsType.All,
    val header: String = "",
    val date: String = "",
    val urlImage: String? = null,
    val article : String = "",
)
