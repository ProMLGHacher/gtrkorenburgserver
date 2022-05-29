package com.orenburg.gtrk.models

data class AddNew(
    val header: String = "",
    val text: String = "",
    val type: NewsType = NewsType.Economy
)
