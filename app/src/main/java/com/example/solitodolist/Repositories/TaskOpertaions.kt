package com.example.solitodolist.Repositories

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.example.solitodolist.data.Task
import com.example.solitodolist.network.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TaskOpertaions {

    suspend fun index(): List<Task> {
        return try {
            RetrofitClient.api.getTasks()
        } catch (e: Exception) {
            Log.e("API_ERROR", "Type d'erreur : ${e::class.java.simpleName}")
            Log.e("API_ERROR", "Message : ${e.message ?: "Aucun message"}")
            emptyList()
        }
    }

    suspend fun show(taskId:Int): Task? {
        return try {
            RetrofitClient.api.getTaskById(taskId)
        } catch (e: Exception) {
            Log.e("API_ERROR", "Type d'erreur : ${e::class.java.simpleName}")
            Log.e("API_ERROR", "Message : ${e.message ?: "Aucun message"}")
            null
        }
    }
    @SuppressLint("CoroutineCreationDuringComposition")
    fun store(task: Task) {

        CoroutineScope(Dispatchers.IO).launch {
            try {
                var response = RetrofitClient.api.storeTask(task=task)
                Log.i("store", "Task added: $response")

            } catch (e: Exception) {
                Log.e("API_ERROR", "Type d'erreur : ${e::class.java.simpleName}")
                Log.e("API_ERROR", "Message : ${e.message ?: "Aucun message"}")
            }
        }
    }
    @SuppressLint("CoroutineCreationDuringComposition")
    fun update(task: Task) {

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = task.id?.let { RetrofitClient.api.updateTask(it, task) }
                Log.i("updatetask", "Task changed: $response")

            } catch (e: Exception) {
                Log.e("API_ERROR", "Erreur : ${e.message}")
            }
        }
    }



    fun delete(taskId: Int){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                RetrofitClient.api.deleteTask(taskId=taskId)

            } catch (e: Exception) {
                Log.e("API_ERROR", "Type d'erreur : ${e::class.java.simpleName}")
                Log.e("API_ERROR", "Message : ${e.message ?: "Aucun message"}")
            }
        }
    }



}