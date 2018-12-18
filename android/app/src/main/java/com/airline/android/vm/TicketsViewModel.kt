package com.airline.android.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.airline.android.BuyTicketsRequest
import com.airline.android.CheckTicketRequest
import com.airline.android.model.JsendFail
import com.airline.android.model.JsendResponse
import com.airline.android.model.Ticket
import com.airline.android.net.AirlineApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class TicketsViewModel: BaseViewModel() {
    @Inject
    lateinit var api: AirlineApi

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
                    when (body.status) {
                        "success" -> tickets.value = body.listData as List<Ticket>?
                        "fail" -> errorCallback("Failure: ${(body.data as JsendFail).message}")
                        "error" -> errorCallback("Error: ${body.message}")
                    }
                }
            }
        }
        api.getTickets().enqueue(callback)
    }

    fun purchase(flightId: Int, seats: List<String>): LiveData<Ticket> {
        val ticket = MutableLiveData<Ticket>()
        val callback = object : Callback<JsendResponse> {
            override fun onFailure(call: Call<JsendResponse>, t: Throwable) {
                errorCallback("Network error: ${t.message}")
            }

            override fun onResponse(call: Call<JsendResponse>, response: Response<JsendResponse>) {
                val body = response.body()
                if (body != null) {
                    when (body.status) {
                        "success" -> ticket.value = body.data as Ticket?
                        "fail" -> errorCallback("Failure: ${(body.data as JsendFail).message}")
                        "error" -> errorCallback("Error: ${body.message}")
                    }
                }
            }
        }
        api.buyTicket(flightId, BuyTicketsRequest(seats)).enqueue(callback)
        return ticket
    }

    fun checkTicket(flightId: Int, seat: String): LiveData<JsendResponse> {
        val result = MutableLiveData<JsendResponse>()
        val callback = object : Callback<JsendResponse> {
            override fun onFailure(call: Call<JsendResponse>, t: Throwable) {
                errorCallback("Network error: ${t.message}")
            }

            override fun onResponse(call: Call<JsendResponse>, response: Response<JsendResponse>) {
                result.value = response.body()
            }
        }
        api.checkTicket(flightId, CheckTicketRequest(seat)).enqueue(callback)
        return result
    }

}
