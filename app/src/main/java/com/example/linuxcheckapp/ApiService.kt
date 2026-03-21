package com.example.linuxcheckapp

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("check-status")
    //調用了supend方法，會暫停當前coroutine的執行，並保留所有局部變量，並在結束後resume
    suspend fun getServerStatus(): Response<ServerStatus>

}
data class ServerStatus(
    val model: String,      // 對應 "model"
    val cpu_load: Int,      // 對應 "cpu_load"
    val status: String      // 對應 "status"
)
