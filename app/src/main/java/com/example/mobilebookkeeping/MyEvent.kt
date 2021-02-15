package com.example.mob

import android.os.Parcelable
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
    var isExpense: Boolean = true,
    var category: String = "",
    var income: Int = 0

) : Parcelable {


    @IgnoredOnParcel
    val date = Date()
    @IgnoredOnParcel

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

//}{
//
//    val date = Date()
//    var title = date.toString().substring(0,10)
//    var isExpanded = false
//    var amount: Int = 0
//    lateinit var comment: String
//    var events =  ArrayList<MyEvent>()
//
//
//    init{
//        if (isDate){
//            this.title = date.toString().substring(0,10)
//            this.comment = ""
//        }else{
//            this.amount = amount
//            this.comment = comment
//        }
//    }


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