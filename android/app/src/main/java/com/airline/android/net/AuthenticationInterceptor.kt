package com.airline.android.net

import com.airline.android.Const
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Class, responsible for adding Authorization header to each request.
 * @see <a href="https://en.wikipedia.org/wiki/Basic_access_authentication">Used auth method</a>
 */
class AuthenticationInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val origin = chain.request()
        val builder = origin.newBuilder()
            .addHeader("Authorization", Const.credentials.toString())
        val request = builder.build()
        return chain.proceed(request)
    }
}
