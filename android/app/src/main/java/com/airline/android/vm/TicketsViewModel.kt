package com.airline.android.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.airline.android.model.JsendFail
import com.airline.android.model.JsendResponse
import com.airline.android.model.Ticket
import com.airline.android.net.AirlineApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class TicketsViewModel : BaseViewModel() {
    @Inject lateinit var api: AirlineApi

    lateinit var tickets: MutableLiveData<List<Ticket>>

    lateinit var errorCallback: (message: String?) -> Unit

    fun getAllTickets(): LiveData<List<Ticket>> {
        if (!::tickets.isInitialized) {
            tickets = MutableLiveData()
            loadAllTickets()
        }
        return tickets
    }

    private fun loadAllTickets() {
        val callback = object : Callback<JsendResponse> {
            override fun onFailure(call: Call<JsendResponse>, t: Throwable) {
                errorCallback("Network error: ${t.message}")
            }

            override fun onResponse(call: Call<JsendResponse>, response: Response<JsendResponse>) {
                val body = response.body()
                if (body != null) {
                    when(body.status) {
                        "success" -> tickets.value = body.listData as List<Ticket>?
                        "fail" -> errorCallback("Failure: ${(body.data as JsendFail).message}")
                        "error" -> errorCallback("Error: ${body.message}")
                    }
                }
            }
        }
        api.getTickets().enqueue(callback)
    }

}
