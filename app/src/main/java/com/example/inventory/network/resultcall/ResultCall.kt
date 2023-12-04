package com.example.inventory.network.resultcall

import com.example.inventory.network.model.ErrorResponse
import com.google.gson.Gson
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Response.success
import java.io.IOException

class ResultCall<T>(val delegate: Call<T>) : Call<Result<T>> {
    override fun clone(): Call<Result<T>> {
        return ResultCall(delegate.clone())
    }

    override fun execute(): Response<Result<T>> {
        return success(Result.success(delegate.execute().body()!!))
    }

    override fun enqueue(callback: Callback<Result<T>>) {
        delegate.enqueue(
            object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    if (response.isSuccessful) {
                        // Handle success response
                        callback.onResponse(
                            this@ResultCall,
                            success(response.code(), Result.success(response.body()!!))
                        )
                    } else {
                        // Handle error response
                        val errorResponse: ErrorResponse = try {
                            val errorBody = response.errorBody()?.string()
                            Gson().fromJson(errorBody, ErrorResponse::class.java)
                        } catch (e: Exception) {
                            ErrorResponse("error", null, "Unknown Error", null)
                        }

                        // Sending the error response to the callback
                        callback.onResponse(
                            this@ResultCall,
                            success(Result.failure(RuntimeException(errorResponse.message)))
                        )
                    }
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    val errorMessage = when (t) {
                        is IOException -> "No internet connection"
                        is HttpException -> t.localizedMessage
                        else -> t.localizedMessage
                    }
                    callback.onResponse(
                        this@ResultCall,
                        success(Result.failure(RuntimeException(errorMessage, t)))
                    )
                }
            }
        )
    }

    override fun isExecuted(): Boolean {
        return delegate.isExecuted
    }

    override fun cancel() {
        delegate.cancel()
    }

    override fun isCanceled(): Boolean {
        return delegate.isCanceled
    }

    override fun request(): Request {
        return delegate.request()
    }

    override fun timeout(): Timeout {
        return delegate.timeout()
    }
}