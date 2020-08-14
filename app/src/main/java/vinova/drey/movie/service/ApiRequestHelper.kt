package vinova.drey.movie.service

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object ApiRequestHelper {
    inline fun <T> asyncRequest(request: Call<T>,
                                crossinline onSuccess: (T?) -> Unit,
                                crossinline onError: (String) -> Unit) {

        request.enqueue(object : Callback<T> {
            override fun onFailure(call: Call<T>, t: Throwable) {
                onError(t.message ?: "unknown error")
                Log.d("ApiRequestHelper", t.message!!)
            }

            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (response.isSuccessful) {
                    Log.d("ApiRequestHelper", "Success")
                    val body = response.body()
                    onSuccess(body)
//                    Log.d("ApiRequestHelper", "${response.body()}")
                } else {
                    Log.d("ApiRequestHelper", "Error")
//                    onError("error code: ${response.code()}")
//                    Log.d("ApiRequestHelper", "${response.code()}")
                }
            }
        })
    }
}