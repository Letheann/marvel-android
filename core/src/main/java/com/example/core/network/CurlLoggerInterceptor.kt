package com.example.core.network

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okio.Buffer
import java.io.IOException
import java.nio.charset.Charset
import java.util.*

class CurlLoggerInterceptor : Interceptor {
    private var curlCommandBuilder: StringBuilder? = null
    private var tag: String? = null

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        curlCommandBuilder = StringBuilder("")
        // add cURL command
        curlCommandBuilder?.append("cURL ")
        curlCommandBuilder?.append("-X ")
        // add method
        curlCommandBuilder?.append(request.method.toUpperCase(Locale.ROOT) + " ")
        // adding headers
        for (headerName in request.headers.names()) {
            addHeader(headerName, request.headers[headerName])
        }

        // adding request body
        val requestBody = request.body
        if (request.body != null) {
            val buffer = Buffer()
            requestBody?.writeTo(buffer)
            var charset = Charset.forName("UTF-8")
            val contentType = requestBody?.contentType()
            if (contentType != null) {
                addHeader("Content-Type", request.body?.contentType().toString())
                charset = contentType.charset(charset)
                curlCommandBuilder?.append(" -d '" + buffer.readString(charset) + "'")
            }
        }

        // add request URL
        curlCommandBuilder?.append(" \"" + request.url.toString() + "\"")
        curlCommandBuilder?.append(" -L")
        CurlPrinter.print(tag, request.url.toString(), curlCommandBuilder.toString())
        return chain.proceed(request)
    }

    private fun addHeader(headerName: String, headerValue: String?) {
        curlCommandBuilder?.append("-H \"$headerName: $headerValue\" ")
    }
}

object CurlPrinter {
    private const val SINGLE_DIVIDER = "────────────────────────────────────────────"
    private var sTag = "CURL"
    fun print(tag: String?, url: String, msg: String?) {
        // setting tag if not null
        if (tag != null) sTag = tag
        val logMsg = java.lang.StringBuilder("\n")
        logMsg.append("\n")
        logMsg.append("URL: $url")
        logMsg.append("\n")
        logMsg.append(SINGLE_DIVIDER)
        logMsg.append("\n")
        logMsg.append(msg)
        logMsg.append(" ")
        logMsg.append(" \n")
        logMsg.append(SINGLE_DIVIDER)
        logMsg.append(" \n ")
        log(logMsg.toString())
    }

    private fun log(msg: String) {
        Log.d(sTag, msg)
    }
}