package com.example.foodonate.model
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserModel(
    val uid: String? = null,
    val name : String? = null,
    val phoneNumber : String? = null,
    val radio : String? = null,
    var profileUrl : String? = null
    ) : Parcelable
