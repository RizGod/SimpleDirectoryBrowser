package com.example.appforvk.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appforvk.R.drawable
import com.example.appforvk.presentation.theme.AppForVKTheme
import com.example.domain.models.MyFile
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun FileListItem(file: MyFile, onClick: (file: MyFile) -> Unit) {
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth()
            .clickable(
                enabled = file.icon == "."
            ) {
                onClick(file)
            },
        elevation = 2.dp

    ) {
        Row(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = getIcon(file.icon)),
                contentDescription = "File image",
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(16.dp)
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = file.name,
                    fontSize = 16.sp
                )
                if (file.icon != ".") {
                    val formatter =
                        SimpleDateFormat("dd MMMM yyyy, HH:mm:ss", Locale.getDefault())
                    val dateString = formatter.format(file.dateOfCreation)
                    Text(
                        text = dateString
                    )
                }
            }
            if (file.icon != ".")
                Text(
                    text = formatFileSize(file.size),
                    modifier = Modifier.padding(8.dp)
                )
        }
    }
}

private fun getIcon(icon: String): Int {
    return when (icon) {
        ".png", ".jpg", ".jpeg", ".gif", ".bmp" -> drawable.image
        ".mp3", ".wav", ".ogg", "midi" -> drawable.audio
        ".mp4", ".rmvb", ".avi", ".flv", ".3gp" -> drawable.video
        ".pdf" -> drawable.pdf
        ".jar", ".zip", ".rar", ".gz" -> drawable.zip
        ".apk" -> drawable.apk
        "." -> drawable.folder
        else -> drawable.file
    }
}

fun formatFileSize(fileSizeBytes: Long): String {
    val units = arrayOf("B", "KB", "MB", "GB", "TB")
    var fileSize = fileSizeBytes.toDouble()
    var index = 0
    while (fileSize > 1024 && index < units.size - 1) {
        fileSize /= 1024
        index++
    }
    return "%.2f %s".format(fileSize, units[index])
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    AppForVKTheme {
        FileListItem(
            file = MyFile(
                name = "Images",
                size = 13L,
                dateOfCreation = Date(),
                icon = ".png"
            )
        )
        {}
    }
}