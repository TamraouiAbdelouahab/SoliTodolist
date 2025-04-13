package com.example.solitodolist.ui.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.solitodolist.Repositories.TaskOpertaions
import com.example.solitodolist.data.Task
import androidx.compose.ui.Alignment
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.solitodolist.viewModel.TasksviewModel

data class TaskFilter(val name: String, val action: () -> Unit)
@Composable
fun TasksScreen(
    navController: NavController,
    tasksviewModel: TasksviewModel = viewModel()
)
{
    val taskList by tasksviewModel.tasks.collectAsState()
    val tasks = remember { mutableStateOf<List<Task>>(emptyList()) }
    var downloadTasks by remember { mutableStateOf(false) }
    LaunchedEffect(taskList) {
        if (taskList.isNotEmpty()) {
            tasks.value = taskList
            downloadTasks = true
        }
    }
    val filterList = listOf(
        TaskFilter("All") {
            tasks.value = taskList
        },
        TaskFilter("Important") {
            tasks.value = taskList?.filter { it.important == 1 }!!
            Log.i("All", taskList.toString())
        },
        TaskFilter("Completed") {
            tasks.value = taskList?.filter { it.status == 1 }!!
        },
    )
    var filter by remember { mutableStateOf(filterList[0].name) }
    if (!downloadTasks)
    {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            LoadingSpinner()
        }

    }
    else
    {
        Column {
            Text(
                text = "Tasks",
                fontSize = 40.sp,
                textAlign = TextAlign.Center,
                color = Color.Blue,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            )
            {
                for (parameter in filterList) {
                    Button(
                        onClick = {
                            filter = parameter.name
                            parameter.action()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (parameter.name == filter) Color.Blue else Color.Gray
                        )
                    ) {
                        Text(text = parameter.name)
                    }
                }
            }
            if (tasks != null)
            {
                LazyColumn {
                    items(tasks!!.value) { task ->
                        TaskCard(task,navController,tasksviewModel)
                    }
                }
            }
        }
    }


}


