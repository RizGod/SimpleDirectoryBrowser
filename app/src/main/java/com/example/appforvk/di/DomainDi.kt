package com.example.appforvk.di

import com.example.domain.usecases.GetDateOfCreationUseCase
import com.example.domain.usecases.GetFileIconUseCase
import com.example.domain.usecases.GetFileNameUseCase
import com.example.domain.usecases.GetFileSizeUseCase
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
}