package com.example.mobilebookkeeping.category

import com.example.mob.MyEvent
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Exclude

class Category {
    var name: String = ""
    //var amount: Float = 0.0f

    @get:Exclude
    var id = ""
    companion object {
        fun fromSnapshot(snapshot: DocumentSnapshot): Category {
            val category = snapshot.toObject(Category::class.java)!!
            category.id = snapshot.id
            return category
        }
    }
}