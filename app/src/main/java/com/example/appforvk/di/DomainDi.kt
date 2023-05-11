package com.example.appforvk.di

import com.example.domain.usecases.*
import org.koin.dsl.module

val domainModule = module {

    factory<GetFileNameUseCase> {
        GetFileNameUseCase(fileRepository = get())
    }
    factory<GetFileSizeUseCase> {
        GetFileSizeUseCase(fileRepository = get())
    }
    factory<GetDateOfCreationUseCase> {
        GetDateOfCreationUseCase(fileRepository = get())
    }
    factory<GetFileIconUseCase> {
        GetFileIconUseCase(fileRepository = get())
    }
    factory<FileIsDirectoryUseCase> {
        FileIsDirectoryUseCase(fileRepository = get())
    }
}