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

    @Composable
    fun store(task: Task) {
        LaunchedEffect(Unit) {
            try {
                withContext(Dispatchers.IO) {
                    RetrofitClient.api.storeTask(task=task)
                }
                //task = result
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
                val response = RetrofitClient.api.updateTask(task.id, task)
                Log.i("update", "Task changed: $response")

            } catch (e: Exception) {
                Log.e("API_ERROR", "Erreur : ${e.message}")
            }
        }
    }


    @Composable
    fun delete(taskId: Int){
        LaunchedEffect(Unit) {
            try {
                withContext(Dispatchers.IO) {
                    RetrofitClient.api.deleteTask(taskId=taskId)
                }
                //task = result
            } catch (e: Exception) {
                Log.e("API_ERROR", "Type d'erreur : ${e::class.java.simpleName}")
                Log.e("API_ERROR", "Message : ${e.message ?: "Aucun message"}")
            }
        }
    }



}