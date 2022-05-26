package com.orenburg.gtrk.models

data class NewsEvent(
    val id: String,
    val type : NewsType,
    val header: String,
    val date: String,
    val urlImage: String?
)
