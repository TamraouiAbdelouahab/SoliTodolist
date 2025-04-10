package com.example.solitodolist

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.solitodolist.ui.theme.SoliTodoListTheme
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.solitodolist.Repositories.TaskOpertaions
import com.example.solitodolist.data.Task
import com.example.solitodolist.ui.components.AddTaskScreen
import com.example.solitodolist.ui.components.Header
import com.example.solitodolist.ui.components.Footer
import com.example.solitodolist.ui.components.TaskScreen
import com.example.solitodolist.ui.components.TasksScreen
import com.example.solitodolist.viewModel.TasksviewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SoliTodoListTheme {
            //    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            //        Greeting(
            //            name = "Android",
            //            modifier = Modifier.padding(innerPadding)
            //        )
            //    }
                AppSoliToDo()
            }
        }
    }
}
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}
@Composable
fun AppSoliToDo(
    navController: NavHostController = rememberNavController(),
    taskViewModel: TasksviewModel = viewModel()
)
{
    Scaffold(
        topBar = {
            Header()
        },
        bottomBar = {
            Footer()
        },
        floatingActionButton = {
            val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
            if (currentRoute == TodoScreens.Tasks.name) {
                FloatingActionButton(onClick = { navController.navigate(TodoScreens.Bakkali.name) }) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                }
            }
        }
    ){innerPadding->
        NavHost(
            navController = navController,
            startDestination = TodoScreens.Tasks.name,
            modifier = Modifier.padding(innerPadding),

        ) {
            composable(route = TodoScreens.Tasks.name)
            {
                // viewmodel :
                //val tasksUiState by taskViewModel.tasks.collectAsState()
                var tasks by remember { mutableStateOf<List<Task>?>(null) }

                LaunchedEffect(Unit) {
                    tasks = TaskOpertaions().index()
                }

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
                    tasks?.let { it1 -> TasksScreen(it1) }
                    //TasksScreen(tasksUiState.TaskList)

                }

            }
            composable(route = TodoScreens.Bakkali.name)
            {
                AddTaskScreen()
            }
        }
    }


}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SoliTodoListTheme {
        AppSoliToDo()
    }
}


enum class TodoScreens{
    Tasks,
    Bakkali
}