package com.example.to_dolist

data class Task(
    val id: String = "",
    val userId: String = "",
    val title: String = "",
    val timestamp: Long = System.currentTimeMillis()
)