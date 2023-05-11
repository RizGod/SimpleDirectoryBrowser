package com.example.domain.models

import java.util.Date

data class MyFile(
    val name: String,
    val size: Long,
    val dateOfCreation: Date,
    val icon: String,
    val isDirectory: Boolean
)