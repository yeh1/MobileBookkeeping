package com.example.mobilebookkeeping

import android.os.Parcelable
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Exclude
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize
import java.util.*


@Parcelize
data class MyEvent(
    var amount: Int = 0,
    var comment: String = "",
    var isDateEvent: Boolean = false,
    var isExpense: Boolean = true
) : Parcelable {


    @IgnoredOnParcel
    val date = Date()
    var title = date.toString().substring(0,10)
    var isExpanded = false
    var events =  ArrayList<MyEvent>()

    init {
        if (isDateEvent){
            var count = 0
            for(e in events){
                if(e.isExpense)
                    count -= e.amount
                else
                    count += e.amount
            }
            if (count >= 0){
                amount = count
                isExpense = false
            }else{
                amount = count
                isExpense = true
            }
        }

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