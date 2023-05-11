package com.example.domain.repository

import java.io.File
import java.util.Date

interface FileRepository {

    fun getFileName(file: File): String

    fun getFileSize(file: File): Long

    fun getDateOfCreation(file: File): Date

    fun getFileIcon(file: File): String

    fun isDirectoryFunction(file: File): Boolean
}