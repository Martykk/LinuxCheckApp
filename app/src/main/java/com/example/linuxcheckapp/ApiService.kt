package com.example.linuxcheckapp

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("api/status")
    //調用了supend方法，會暫停當前coroutine的執行，並保留所有局部變量，並在結束後resume
    suspend fun getServerStatus(): Response<ServerStatus>

    @GET("api/uptime/{serviceName}")
    suspend fun getServiceUptime(
        @Path("serviceName") name: String
    ): Response<String>

}
data class ServerStatus(
    val model: String,
    val cpu_load: Float,
    val is_online: Boolean
)
