package com.example.appforvk.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.example.appforvk.R

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SortingButton() {
    val menuItems = listOf("Item 1", "Item 2", "Item 3")
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier
        .fillMaxSize()
        .combinedClickable(
            onClick = {},
            onLongClick = {}
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
            menuItems.forEach { item ->
                DropdownMenuItem(
                    onClick = {
                        expanded = false
                    }
                ) {
                    Text(item)
                }
            }
        }
    }
}