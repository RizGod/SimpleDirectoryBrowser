package com.example.domain.usecases

import com.example.domain.repository.FileRepository
import java.io.File
import java.util.Date

class GetDateOfCreationUseCase(
    private val fileRepository: FileRepository
) {


    fun execute(file: File): Date {
        return fileRepository.getDateOfCreation(file = file)
    }
}