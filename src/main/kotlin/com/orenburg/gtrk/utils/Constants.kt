package com.orenburg.gtrk.utils

import java.time.format.DateTimeFormatter


val serverUrl = "https://43f5-136-169-243-169.ngrok.io/"

val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
val imageUrl = serverUrl + "files/"