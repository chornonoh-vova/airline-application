package com.airline.android.model

import com.google.gson.GsonBuilder
import org.junit.Assert
import org.junit.Test

class TestGsonUtils {
    private val gson = GsonBuilder().registerTypeAdapter(JsendResponse::class.java, JsendDeserializer()).create()

    @Test
    fun testDeserializationOne() {
        val json = """
        {
          "status": "success",
          "data": {
            "routeId": 0,
            "departureAirport": "Kyiv Boryspil International Airport",
            "destinationAirport": "Antwerp Airport",
            "price": 500,
            "duration": "05:30:00"
          }
        }
        """.trimIndent()

        val obj = gson.fromJson(json, JsendResponse::class.java)
        Assert.assertEquals(Route::class.qualifiedName, obj.data!!::class.qualifiedName)
    }

    @Test
    fun testDeserializationList() {
        val json = """
        {
          "status": "success",
          "data": [
            {
              "routeId": 0,
              "departureAirport": "Kyiv Boryspil International Airport",
              "destinationAirport": "Antwerp Airport",
              "price": 500,
              "duration": "05:30:00"
            }
          ]
        }
        """.trimIndent()
        val obj = gson.fromJson(json, JsendResponse::class.java)
        Assert.assertEquals(ArrayList::class.qualifiedName, obj.listData!!::class.qualifiedName)
    }

    @Test
    fun testFailData() {
        val json = """
            {
              "status": "fail",
              "data": {
                "message": "Failure message"
              }
            }
        """.trimIndent()
        val obj = gson.fromJson(json, JsendResponse::class.java)
        Assert.assertEquals("Failure message", (obj.data as JsendFail).message)
        Assert.assertEquals(JsendFail::class.qualifiedName, obj.data!!::class.qualifiedName)
    }

    @Test
    fun testErrorMessage() {
        val json = """
            {
              "status": "error",
              "message": "Error message"
            }
        """.trimIndent()
        val obj = gson.fromJson(json, JsendResponse::class.java)
        Assert.assertEquals("Error message", obj.message)
    }
}
