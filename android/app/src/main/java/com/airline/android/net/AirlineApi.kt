package com.airline.android.net

import com.airline.android.AddPassengerRequest
import com.airline.android.LoginRequest
import com.airline.android.model.JsendResponse
import retrofit2.Call
import retrofit2.http.*

/**
 * REST API interface
 * @see <a href="https://github.com/hbvhuwe/airline-application/wiki/Airline-API">API spec</a>
 */
interface AirlineApi {
    @GET("/routes")
    fun getRoutes(): Call<JsendResponse>

    @GET("/routes/{routeId}")
    fun getRoute(@Path("routeId") routeId: Int): Call<JsendResponse>

    @GET("/routes/{routeId}/flights")
    fun getFlightsForRoute(@Path("routeId") routeId: Int): Call<JsendResponse>

    @GET("/flights")
    fun getFlights(): Call<JsendResponse>

    @GET("tickets")
    fun getTickets(): Call<JsendResponse>

    @GET("/search/flights")
    fun searchFlights(
        @Query("from") from: String?,
        @Query("to") to: String,
        @Query("sort") sort: String = "none",
        @Query("order") order: String = "desc"
    ): Call<JsendResponse>

    @GET("/search/routes")
    fun searchRoutes(
        @Query("from") from: String?,
        @Query("to") to: String,
        @Query("sort") sort: String = "none",
        @Query("order") order: String = "desc"
    ): Call<JsendResponse>

    @POST("/login")
    fun login(@Body loginRequest: LoginRequest): Call<JsendResponse>

    @POST("/register")
    fun register(@Body signUpRequest: LoginRequest): Call<JsendResponse>

    @POST("/users/{userId}/passengerInfo")
    fun addPassengerInfo(
        @Path("userId") userId: Int,
        @Body addPassengerRequest: AddPassengerRequest
    ): Call<JsendResponse>
}
