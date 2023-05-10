package com.example.appforvk.di

import com.example.data.repository.FileRepositoryImpl
import com.example.domain.repository.FileRepository
import org.koin.dsl.module

val dataModule = module {

    single<FileRepository> {
        FileRepositoryImpl()
    }
}