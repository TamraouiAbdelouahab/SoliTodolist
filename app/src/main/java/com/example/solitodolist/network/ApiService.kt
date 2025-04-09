package com.example.solitodolist.network

import retrofit2.Call
import retrofit2.http.GET
import com.example.solitodolist.data.Task
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
interface ApiService {
    //@GET("todos/2")
    //fun getTaskById(@Path("id") id: Int): Call<Task>
    //fun getTaskById(): Call<Task>

    @GET("api/tasks/1")
    suspend fun getTask(): Task

    @GET("api/tasks")
    suspend fun getTasks(): List<Task>
    @PUT("api/tasks/{id}")
    suspend fun updateTask(
        @Path("id") taskId: Int,
        @Body task: Task
    ) : Task
}