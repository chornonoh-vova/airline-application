package com.airline.android.model

import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

/**
 * Type tokens needed for Gson deserialization of generic types
 */
val listRoutesType: Type = object : TypeToken<List<Route>>() {}.type

/**
 * Helper class, represents API response
 */
data class JsendResponse(
    var status: String,
    var data: Data?,
    var listData: List<Data>?,
    var message: String?
)

/**
 * Helper class, represents fail data
 */
data class JsendFail(var message: String) : Data

/**
 * Interface-marker for classes, that can be deserialized
 */
interface Data

/**
 * Custom deserializer for Gson
 */
class JsendDeserializer : JsonDeserializer<JsendResponse> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ) : JsendResponse {
        val jsendObject = json.asJsonObject

        val gson = Gson()

        val status = jsendObject.get("status").asString

        val result = JsendResponse(status, null, null, null)

        when (status) {
            "success" -> {
                val data = jsendObject.get("data")
                if (data.isJsonArray) {
                    val e = data.asJsonArray[0]
                    if (e.isJsonObject) {
                        if (e.asJsonObject.has("routeId")) {
                            result.listData = gson.fromJson(data, listRoutesType)
                        }
                    } else {
                        result.listData = emptyList()
                    }
                } else {
                    if (data.asJsonObject.has("routeId")) {
                        result.data = gson.fromJson(data.asJsonObject, Route::class.java)
                    }
                }
            }
            "fail" -> {
                val data = jsendObject.get("data")
                result.data = gson.fromJson(data, JsendFail::class.java)
            }
            "error" -> {
                result.message = jsendObject.get("message").asString
            }
        }
        return result
    }
}
