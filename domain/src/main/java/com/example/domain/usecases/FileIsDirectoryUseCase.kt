package com.example.domain.usecases

import com.example.domain.repository.FileRepository
import java.io.File

class FileIsDirectoryUseCase(
    private val fileRepository: FileRepository
) {

    fun execute(file: File): Boolean {
        return fileRepository.isDirectoryFunction(file = file)
    }
}