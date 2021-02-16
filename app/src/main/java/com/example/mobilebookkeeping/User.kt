package com.example.mobilebookkeeping

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Exclude

class User (
        var username: String = "",
//        var uid: String = "",
        var budget: String = ""
){

    @get:Exclude
    var id = ""

    companion object {

        fun fromSnapshot(snapshot: DocumentSnapshot): User {
            val user = snapshot.toObject(User::class.java)!!
            user.id = snapshot.id
            return user
        }
    }
}