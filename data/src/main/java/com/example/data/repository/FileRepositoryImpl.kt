package com.example.data.repository

import android.webkit.MimeTypeMap
import com.example.domain.repository.FileRepository
import java.io.File
import java.util.*

class FileRepositoryImpl : FileRepository {

    override fun getFileName(file: File): String {
        return file.name
    }

    override fun getFileSize(file: File): Long {
        return file.length()
    }

    override fun getDateOfCreation(file: File): Date {
        val dateInMillis = file.lastModified()
        return Date(dateInMillis)
    }

    override fun getFileIcon(file: File): String {
        val ext = MimeTypeMap.getFileExtensionFromUrl(file.name)
        return ".$ext"
    }


}