package com.airline.android.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.airline.android.model.JsendFail
import com.airline.android.model.JsendResponse
import com.airline.android.model.Route
import com.airline.android.net.AirlineApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class RoutesViewModel(var routeId: Int = 0) : BaseViewModel() {
    @Inject
    lateinit var api: AirlineApi

    private lateinit var routes: MutableLiveData<List<Route>>
    private lateinit var route: MutableLiveData<Route>

    lateinit var errorCallback: (message: String?) -> Unit

    fun getAllRoutes(): LiveData<List<Route>> {
        if (!::routes.isInitialized) {
            routes = MutableLiveData()
            loadAllRoutes()
        }
        return routes
    }

    private fun loadAllRoutes() {
        val callback = object : Callback<JsendResponse> {
            override fun onFailure(call: Call<JsendResponse>, t: Throwable) {
                errorCallback("Network error ${t.message}")
            }

            override fun onResponse(call: Call<JsendResponse>, response: Response<JsendResponse>) {
                val body = response.body()
                if (body != null) {
                    when(body.status) {
                        "success" -> routes.value = body.listData as List<Route>?
                        "fail" -> errorCallback((body.data as JsendFail).message)
                        "error" -> errorCallback(body.message)
                    }
                }
            }
        }
        api.getRoutes().enqueue(callback)
    }

    fun getRoute(): LiveData<Route> {
        if (!::route.isInitialized) {
            route = MutableLiveData()
            loadRoute(routeId)
        }
        return route
    }

    private fun loadRoute(routeId: Int) {
        val callback = object: Callback<JsendResponse> {
            override fun onFailure(call: Call<JsendResponse>, t: Throwable) {
                errorCallback("Network error ${t.message}")
            }

            override fun onResponse(call: Call<JsendResponse>, response: Response<JsendResponse>) {
                val body = response.body()
                if (body != null) {
                    when(body.status) {
                        "success" -> route.value = body.data as Route?
                        "fail" -> errorCallback((body.data as JsendFail).message)
                        "error" -> errorCallback(body.message)
                    }
                }
            }
        }
        api.getRoute(routeId).enqueue(callback)
    }

    fun searchRoutes(
        from: String?,
        to: String,
        sort: String?,
        order: String?
    ) {
        val callback = object : Callback<JsendResponse> {
            override fun onFailure(call: Call<JsendResponse>, t: Throwable) {
                errorCallback("Network error ${t.message}")
            }

            override fun onResponse(call: Call<JsendResponse>, response: Response<JsendResponse>) {
                val body = response.body()
                if (body?.status == "success") {
                    routes.value = body.listData as List<Route>?
                } else {
                    if (body?.data != null) {
                        errorCallback((body.data as JsendFail).message)
                    } else {
                        errorCallback(body?.message)
                    }
                }
            }
        }
        api.searchRoutes(from, to, sort ?: "none", order ?: "desc").enqueue(callback)
    }
}
