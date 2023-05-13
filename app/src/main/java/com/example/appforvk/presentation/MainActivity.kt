package com.example.appforvk.presentation

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.appforvk.presentation.theme.AppForVKTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import org.koin.android.BuildConfig
import org.koin.androidx.viewmodel.ext.android.viewModel

@OptIn(ExperimentalPermissionsApi::class)
class MainActivity : ComponentActivity() {

    private val viewModel: MyViewModel by viewModel()

    private val permissions = listOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    private lateinit var permissionState: MultiplePermissionsState


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("OnCreateActivity", "onCreate: ")

        setContent {
            AppForVKTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    FilesManagerWithPermissionsCheck()
                }
                BackHandler(onBack = {
                    viewModel.toParentDirectory()
                })
            }
        }
    }

    @Composable
    private fun FilesManagerWithPermissionsCheck() {
        permissionState = rememberMultiplePermissionsState(permissions = permissions)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && !Environment.isExternalStorageManager() ||
            Build.VERSION.SDK_INT < Build.VERSION_CODES.R && !permissionState.allPermissionsGranted
        ) {
            PermissionStartPage {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) requestManageFilesPermissions()
                else permissionState.launchMultiplePermissionRequest()
            }
        } else {
//            val files by viewModel.files.observeAsState()
            Files(viewModel)
        }
    }


    @RequiresApi(Build.VERSION_CODES.R)
    private fun requestManageFilesPermissions() {
        try {
            val uri = Uri.parse("package:${BuildConfig.LIBRARY_PACKAGE_NAME}")
            val intent = Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION, uri)
            startActivity(intent)
        } catch (e: Exception) {
            val intent = Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION)
            startActivity(intent)
        }
    }
}


@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AppForVKTheme {
        Greeting("Android")
    }
}