package com.example.solitodolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.example.solitodolist.ui.theme.SoliTodoListTheme
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.solitodolist.ui.components.Header
import com.example.solitodolist.ui.components.Footer
import com.example.solitodolist.ui.components.TaskScreen

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
                TaskScreen("this is Tasks Screen",{ navController.navigate(TodoScreens.Bakkali.name) })
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