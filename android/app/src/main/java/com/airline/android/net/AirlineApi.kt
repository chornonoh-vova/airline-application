package com.airline.android.net

import com.airline.android.model.JsendResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * REST API interface
 * @see <a href="https://github.com/hbvhuwe/airline-application/wiki/Airline-API">API spec</a>
 */
interface AirlineApi {
    @GET("/routes")
    fun getRoutes(): Call<JsendResponse>

    @GET("/search/routes")
    fun searchRoutes(
        @Query("from") from: String?,
        @Query("to") to: String,
        @Query("sort") sort: String = "none",
        @Query("order") order: String = "desc"
    ): Call<JsendResponse>
}
