package com.airline.android.net

import com.airline.android.model.Route
import retrofit2.Call
import retrofit2.http.GET

/**
 * REST API interface
 * @see <a href="https://github.com/hbvhuwe/airline-application/wiki/Airline-API">API spec</a>
 */
interface AirlineApi {
    @GET("/routes")
    fun getRoutes(): Call<List<Route>>
}
