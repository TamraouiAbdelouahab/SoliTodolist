package com.example.solitodolist.ui.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.example.solitodolist.data.Task


@Composable
fun TasksScreen(
    tasks:List<Task>
)
{
    LazyColumn {
        items(tasks) { task ->
            TaskCard(task)
        }
    }
}