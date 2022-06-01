package com.orenburg.gtrk.utils

import java.time.format.DateTimeFormatter


val serverUrl = "https://server.krea-company.keenetic.pro/"

val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
val imageUrl = serverUrl + "files/"