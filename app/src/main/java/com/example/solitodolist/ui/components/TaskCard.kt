
package com.example.solitodolist.ui.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.solitodolist.Repositories.TaskOpertaions
import com.example.solitodolist.data.Task
import com.example.solitodolist.network.RetrofitClient
import com.example.solitodolist.viewModel.TasksviewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun TaskCard(
    task:Task,
    navController: NavController,
    tasksviewModel: TasksviewModel = viewModel()
) {
    var completed by remember { mutableStateOf(task.status) }
    var important by remember { mutableStateOf(task.important) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                navController.navigate("editTask/"+task.id)
            },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(30.dp)
                    .clip(CircleShape)
                        .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                IconButton(onClick = {
                    completed = if (completed == 1) 0 else 1
                    task.status = completed
                    TaskOpertaions().update(task)
                }) {
                    if (completed == 1){
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Tâche complétée",
                        tint = Color.Black
                    )
                }
            }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = task.title,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = {
                important = if (important == 1) 0 else 1
                task.important = important
                TaskOpertaions().update(task)
            }) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Important",
                    tint = if (important == 1) Color(0xFFFFC107) else Color.Gray
                )
            }
            IconButton(onClick = {
                task.id?.let { TaskOpertaions().delete(it) }
                tasksviewModel.deleteTask(task)
            }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Important",
                    tint = Color.Red
                )
            }
        }
    }
}

fun update(task: Task)
{
    CoroutineScope(Dispatchers.IO).launch {
        try {
            val response = task.id?.let { RetrofitClient.api.updateTask(it, task) }
            Log.i("update", "Task changed: $response")

        } catch (e: Exception) {
            Log.e("API_ERROR", "Erreur : ${e.message}")
        }
    }
}
