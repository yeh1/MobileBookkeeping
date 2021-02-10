package com.example.mobilebookkeeping

import android.os.Parcel
import android.os.Parcelable
import kotlin.math.pow

class MyEvent(amount: Int, comment: String){

    var amount: Int = 0
    lateinit var comment: String


    init{
        this.amount = amount
        this.comment = comment
    }


//    override fun writeToParcel(parcel: Parcel, flags: Int) {
////        parcel.writeString(questionType)
////        parcel.writeInt(index)
////        parcel.writeString(question)
//    }
//
//    override fun describeContents(): Int {
//        return 0
//    }

//    companion object CREATOR : Parcelable.Creator<MyEvent> {
//        override fun createFromParcel(parcel: Parcel): MyEvent {
//            return MyEvent(parcel)
//        }
//
//        override fun newArray(size: Int): Array<MyEvent?> {
//            return arrayOfNulls(size)
//        }
//    }


}