package com.example.solitodolist

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.example.solitodolist.ui.theme.SoliTodoListTheme
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.solitodolist.data.Task
import com.example.solitodolist.network.RetrofitClient
import com.example.solitodolist.ui.components.Header
import com.example.solitodolist.ui.components.Footer
import com.example.solitodolist.ui.components.TaskScreen
import com.example.solitodolist.ui.components.TaskCard
import com.example.solitodolist.ui.components.TasksScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

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
)
{
    Scaffold(
        topBar = {
            Header()
        },
        bottomBar = {
            Footer()
        }

    ){innerPadding->
        NavHost(
            navController = navController,
            startDestination = TodoScreens.Bakkali.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = TodoScreens.Tasks.name)
            {
                var tasks by remember { mutableStateOf<List<Task>?>(null) }
                var error by remember { mutableStateOf<String?>(null) }

                LaunchedEffect(Unit) {
                    try {
                        val result = withContext(Dispatchers.IO) {
                            RetrofitClient.api.getTasks()
                        }
                        tasks = result

                    } catch (e: Exception) {
                        Log.e("API_ERROR", "Type d'erreur : ${e::class.java.simpleName}")
                        Log.e("API_ERROR", "Message : ${e.message ?: "Aucun message"}")
                        error = e.message ?: "Erreur inconnue (${e::class.java.simpleName})"
                    }
                }

                tasks?.let { it1 -> TasksScreen(it1) }
            }
            composable(route = TodoScreens.Bakkali.name)
            {
                TaskScreen("this is Bakkali Screen",{ navController.navigate(TodoScreens.Tasks.name) })
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