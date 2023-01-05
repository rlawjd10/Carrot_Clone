package com.clone.karrot.data

import androidx.annotation.DrawableRes

data class Posting(
    @DrawableRes
    val image: Int,
    val title: String,
    val price: String,
    val time: String,
    val region: String
)
