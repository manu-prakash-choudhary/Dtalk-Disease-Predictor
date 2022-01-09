package com.example.dtalk.Model_Class
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable


@Parcelize
class symptoms_nameList(val sympName: String?=null): Parcelable
{


    constructor (): this("")

}

//class Employee : Serializable {
//    var isChecked = false
//    var name: String? = null
//}