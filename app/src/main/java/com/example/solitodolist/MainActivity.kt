package com.example.solitodolist

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.solitodolist.ui.theme.SoliTodoListTheme
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.solitodolist.data.Task
import com.example.solitodolist.ui.components.AddTaskScreen
import com.example.solitodolist.ui.components.Header
import com.example.solitodolist.ui.components.Footer
import com.example.solitodolist.ui.components.Routes
import com.example.solitodolist.ui.components.TasksScreen
import com.example.solitodolist.ui.components.editTaskScreen
import com.example.solitodolist.viewModel.TasksviewModel

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SoliTodoListTheme {
                AppSoliToDo()
            }
        }
    }
}
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppSoliToDo(
    navController: NavHostController = rememberNavController(),
    tasksviewModel: TasksviewModel = viewModel()
)
{

    Scaffold(
        topBar = {
            Header(navController)
        },
        bottomBar = {
                Footer(navController)
        }
    ){innerPadding->
        NavHost(
            navController = navController,
            startDestination = Routes.Tasks.name,
            modifier = Modifier.padding(innerPadding),
        ) {
            composable(route = Routes.Tasks.name)
            {
                TasksScreen(navController)
            }
            composable(route = Routes.AddTask.name)
            {
                AddTaskScreen(toNavigate = {
                        navController.navigate(Routes.Tasks.name)
                })
            }
            composable(
                route = Routes.EditTask.name +"/{taskId}")
             {
                val taskId = it.arguments?.getString("taskId")?.toInt()
                 if (taskId != null) {
                     editTaskScreen(
                         navController = navController,
                         taskId = taskId
                     )
                 }
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SoliTodoListTheme {
        AppSoliToDo()
    }
}


