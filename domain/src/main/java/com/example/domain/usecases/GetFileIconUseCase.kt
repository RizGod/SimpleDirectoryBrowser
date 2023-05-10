package com.example.domain.usecases

import com.example.domain.repository.FileRepository
import java.io.File

class GetFileIconUseCase(
    private val fileRepository: FileRepository
) {

    fun execute(file: File): String {
        return fileRepository.getFileIcon(file = file)
    }
}