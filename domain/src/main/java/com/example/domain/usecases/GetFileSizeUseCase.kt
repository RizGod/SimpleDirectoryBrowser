package com.example.domain.usecases

import com.example.domain.repository.FileRepository
import java.io.File

class GetFileSizeUseCase(
    private val fileRepository: FileRepository
) {

    fun execute(file: File): Long {
        return fileRepository.getFileSize(file = file)
    }
}