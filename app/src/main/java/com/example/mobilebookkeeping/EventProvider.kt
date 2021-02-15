package com.example.mobilebookkeeping



interface EventProvider {
    fun sendEvents(event: MyEvent)
}