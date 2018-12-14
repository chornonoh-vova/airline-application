package com.airline.android.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.airline.android.AddPassengerRequest
import com.airline.android.LoginRequest
import com.airline.android.model.JsendResponse
import com.airline.android.net.AirlineApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class SignUpViewModel(var userId: Int = 0) : BaseViewModel() {
    @Inject
    lateinit var api: AirlineApi

    fun tryToSignUp(email: String, password: String): LiveData<JsendResponse> {
        val resp = MutableLiveData<JsendResponse>()
        val callback = object : Callback<JsendResponse> {
            override fun onFailure(call: Call<JsendResponse>, t: Throwable) {
                // TODO: show error message
            }

            override fun onResponse(call: Call<JsendResponse>, response: Response<JsendResponse>) {
                resp.value = response.body()
            }
        }
        api.register(LoginRequest(email, password)).enqueue(callback)
        return resp
    }

    fun addPassengerInfo(firstName: String, lastName: String, address: String, phone: String): LiveData<JsendResponse> {
        val resp = MutableLiveData<JsendResponse>()
        val callback = object : Callback<JsendResponse> {
            override fun onFailure(call: Call<JsendResponse>, t: Throwable) {
                // TODO: show error message
            }

            override fun onResponse(call: Call<JsendResponse>, response: Response<JsendResponse>) {
                resp.value = response.body()
            }
        }
        api.addPassengerInfo(userId, AddPassengerRequest(firstName, lastName, address, phone)).enqueue(callback)
        return resp
    }

}
