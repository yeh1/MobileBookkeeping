package com.example.mob


interface EventProvider {
    fun sendEvents(event: MyEvent)
}