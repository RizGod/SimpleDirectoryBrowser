package com.example.appforvk.presentation

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import com.example.appforvk.R
import com.example.domain.models.MyFile
import java.io.File


private const val AUTHORITY_FILE_PROVIDER = "com.example.appforvk.fileprovider"

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Files(viewModel: MyViewModel) {

    val context = LocalContext.current

    val openFileLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {}

    val fileList by viewModel.files.observeAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        item {
            var expanded by remember { mutableStateOf(false) }
            var sortType by remember { mutableStateOf("Extension") }
            var ascending by remember { mutableStateOf(true) }

            Box(modifier = Modifier
                .padding(8.dp)
                .fillMaxSize()
                .combinedClickable(
                    onClick = {
                        ascending = !ascending
                        val comparator = when (sortType) {
                            "Size" -> compareBy { it.size }
                            "Date of creation" -> compareBy { it.dateOfCreation }
                            else -> compareBy<MyFile> { it.icon.length }.thenBy { it.name }
                        }
                        viewModel.sortFiles(comparator, ascending)
                    },
                    onLongClick = { expanded = true }
                )
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.sort_image),
                    contentDescription = "Sorting button",
                    colorFilter = ColorFilter.tint(
                        LocalContentColor.current.copy(alpha = LocalContentAlpha.current)
                    )
                )
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    viewModel.menuItems.forEach { item ->
                        DropdownMenuItem(
                            onClick = {
                                expanded = false
                                sortType = item
                                val comparator = when (sortType) {
                                    "Size" -> compareBy { it.size }
                                    "Date of creation" -> compareBy { it.dateOfCreation }
                                    else -> compareBy<MyFile> { it.icon }.thenBy { it.name }
                                }
                                viewModel.sortFiles(comparator, ascending)
                            }
                        ) {
                            Text(item)
                        }
                    }
                }
            }
        }

        if (fileList?.isEmpty() != false)
            item {
                Text(
                    text = "Directory is empty",
                    fontSize = 32.sp,
                    modifier = Modifier
                        .fillMaxSize(),
                    textAlign = TextAlign.Center
                )
            }
        else
            items(fileList ?: emptyList(), key = { it.hashCode() }) { file ->
                AnimatedVisibility(
                    visible = true,
                    modifier = Modifier.animateItemPlacement()
                ) {
                    FileListItem(file = file) {
                        if (viewModel.openFileOrDirectory(it)) {
                            val path = viewModel.getCurrentDirectory() + "/${it.name}"
                            val uri = FileProvider.getUriForFile(
                                context,
                                AUTHORITY_FILE_PROVIDER,
                                File(path)
                            )
                            val intent = Intent(Intent.ACTION_VIEW).apply {
                                setDataAndType(uri, "*/*")
                                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                            }
                            openFileLauncher.launch(intent)
                        }
                    }
                }
            }
    }
}