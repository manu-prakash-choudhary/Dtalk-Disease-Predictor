package com.example.dtalk.Model_Class

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class appUsers(val ImageUrl: String, val cityname: String, val countryname: String,
               val profession: String, val uid : String?=null, val username: String): Parcelable {


    constructor() : this("", "", "", "", "", "")


}

