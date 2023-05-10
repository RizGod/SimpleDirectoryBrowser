package com.example.domain.usecases

import com.example.domain.repository.FileRepository
import java.io.File

class GetFileNameUseCase(
    private val fileRepository: FileRepository
) {

    fun execute(file: File): String {
        return fileRepository.getFileName(file = file)
    }
}