package com.example.appforvk.presentation

import android.os.Environment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.models.MyFile
import com.example.domain.usecases.*
import java.io.File

class MyViewModel(
    private val getFileNameUseCase: GetFileNameUseCase,
    private val getFileSizeUseCase: GetFileSizeUseCase,
    private val getFileDateOfCreationUseCase: GetDateOfCreationUseCase,
    private val getFileIconUseCase: GetFileIconUseCase,
    private val isDirectoryUseCase: FileIsDirectoryUseCase
) : ViewModel() {
    val menuItems = listOf("Size", "Date of creation", "Extension")


    private var directoryFiles = Environment.getExternalStorageDirectory()

    private val _files =
        MutableLiveData(getFiles(directoryFiles.listFiles()).sortedBy { it.name })

    val files: LiveData<List<MyFile>> get() = _files

    fun openFileOrDirectory(directory: MyFile) {
        directoryFiles = File("${directoryFiles.path}/${directory.name}")
        _files.value = getFiles(directoryFiles.listFiles())
    }

    fun sortFiles(comparator: Comparator<MyFile>, ascending: Boolean) {
        _files.value =
            if (!ascending)
                files.value?.sortedWith(comparator)
            else
                files.value?.sortedWith(
                    comparator
                )?.reversed() ?: emptyList()
    }

    fun toParentDirectory(): Boolean {
        if (directoryFiles.path != Environment.getExternalStorageDirectory().path) {
            directoryFiles = directoryFiles.parent?.let { File(it) }
            _files.value = getFiles(directoryFiles.listFiles())
            return false
        }
        return true
    }


    private fun getFiles(files: Array<File>?) = files?.map {
        MyFile(
            name = getFileNameUseCase.execute(it),
            size = getFileSizeUseCase.execute(it),
            dateOfCreation = getFileDateOfCreationUseCase.execute(it),
            icon = getFileIconUseCase.execute(it),
            isDirectory = isDirectoryUseCase.execute(it)
        )
    }?.toList() ?: emptyList()
}