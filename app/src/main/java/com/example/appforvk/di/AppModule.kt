package com.example.appforvk.di

import com.example.appforvk.presentation.MyViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel {
        MyViewModel(
            getFileNameUseCase = get(),
            getFileSizeUseCase = get(),
            getFileDateOfCreationUseCase = get(),
            getFileIconUseCase = get()
        )
    }
}