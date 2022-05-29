package com.orenburg.gtrk.models

data class NewsEvent(
    val id: String,
    val type : NewsType,
    val header: String,
    val date: String,
    val urlImage: String? = "https://43f5-136-169-243-169.ngrok.io/files/default-image.png"
)
