package com.example.foodonate.model

import java.util.*

data class PostModel(
    val name : String? = null,
    val timeToPrep : String? = null,
    val  quantity : String? = null,
    val address : String?= null,
    val utensils: String? =null,
    val date : String? = null,
    val imageUrl : String? = null
)
