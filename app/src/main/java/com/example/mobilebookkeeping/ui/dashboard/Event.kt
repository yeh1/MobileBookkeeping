package com.example.mobilebookkeeping.ui.dashboard

import android.os.Parcel
import android.os.Parcelable

class Event() {

    val isExpense = true
    val amount : Int = 0
    private val category= arrayListOf("food", "travel")
    val comment: String = ""

//    constructor(parcel: Parcel) : this(
//            parcel.readString(),
//            parcel.readInt()) {
//        question = parcel.readString().toString()
//    }


    init{

    }



   // override fun writeToParcel(parcel: Parcel, flags: Int) {
//        parcel.writeString(questionType.toString())
//        parcel.writeInt(index)
////        //parcel.writeString()
//    }
//
//    override fun describeContents(): Int {
//        return 0
//    }

//    companion object CREATOR : Parcelable.Creator<Event> {
//        override fun createFromParcel(parcel: Parcel): Event {
//            return Event(parcel)
//        }
//
//        override fun newArray(size: Int): Array<Event?> {
//            return arrayOfNulls(size)
//        }
//    }


}