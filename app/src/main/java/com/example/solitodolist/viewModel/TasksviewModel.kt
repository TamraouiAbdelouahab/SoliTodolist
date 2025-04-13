package com.example.solitodolist.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.solitodolist.Repositories.TaskOpertaions
import com.example.solitodolist.data.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TasksviewModel: ViewModel() {

    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks.asStateFlow()
    init{
        fetchTasks()
    }
    private fun fetchTasks() {
        viewModelScope.launch {
            try {
                val result = TaskOpertaions().index()
                _tasks.value = result
                Log.i("StateTest", tasks.value.size.toString())
            }
            catch (e: Exception)
            {
                Log.i("StateTest", "error")
            }
        }
    }
    fun deleteTask(taskToDelete: Task) {
        _tasks.value = _tasks.value.filter { it.id != taskToDelete.id }
    }
}