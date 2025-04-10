package com.example.solitodolist.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.solitodolist.Repositories.TaskOpertaions
import com.example.solitodolist.data.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TasksviewModel: ViewModel() {

    private val _tasks = MutableStateFlow(TaskListUIState())
    val tasks = _tasks.asStateFlow()
    val task = "abdel"

    init{
        fetchTasks()
    }
    private fun fetchTasks()
    {
        viewModelScope.launch {
            try {
                _tasks.value.TaskList = TaskOpertaions().index()
                Log.i("StateTest", tasks.value.TaskList.get(0).title)
            }
            catch (e: Exception)
            {
                Log.i("StateTest", "error")
            }

        }


    }




}