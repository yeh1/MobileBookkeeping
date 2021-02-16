package com.example.mobilebookkeeping

import android.os.Parcelable
import android.util.Log
import com.example.mobilebookkeeping.category.Category
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Exclude
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
import java.util.*


@Parcelize
data class MyEvent(
    var amount: Int = 0,
    var comment: String = "",
    var isDateEvent: Boolean = false,
    var uid: String = "",
    var isExpense: Boolean = true,
    var category: String = "",
    var income: Int = 0

) : Parcelable {


    @IgnoredOnParcel
    val date = Date()
    @IgnoredOnParcel

    var month = date.toString().substring(4,7)
    var title = date.toString().substring(0,10)
    var isExpanded = false
    var events =  ArrayList<MyEvent>()
    init {

    }
    @get:Exclude
    var id = ""
    companion object {
        fun fromSnapshot(snapshot: DocumentSnapshot): MyEvent{
            val event = snapshot.toObject(MyEvent::class.java)!!
            event.id = snapshot.id
            return event
        }
    }

    fun updateAmount(){
        amount = 0
        income = 0
        if (isDateEvent){
            for(e in events){
                if(e.isExpense)
                    amount += e.amount
                else{
                    income += e.amount
                }
            }
        }
    }


}