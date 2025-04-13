package com.example.solitodolist.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState


@Composable
fun Footer(navController: NavController){
    val currentRoute =  navController.currentBackStackEntryAsState().value?.destination?.route
    if (currentRoute == Routes.Tasks.name)
    {
        BottomAppBar(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.primary,
        ) {
            Row(modifier =Modifier.padding(10.dp).clickable {
                navController.navigate(Routes.AddTask.name)
            })
            {
                Icon(Icons.Default.Add, contentDescription = "Add")
                Text(text= "Add Task")
            }
        }
    }

}
