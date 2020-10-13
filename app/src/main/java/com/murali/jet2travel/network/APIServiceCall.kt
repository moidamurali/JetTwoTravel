/**
 * Created by Murali Mohan on 13/10/2020.
 */

package com.murali.jet2travel.network

import Articles
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import okio.Buffer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class APIServiceCall private constructor() {
    var mAPIServices: APIServices? = null
    var endPointURL: String? = null

    fun hitServiceServiceCall(): APIServices {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor { chain ->
            val original = chain.request()
            val originalHttpUrl = original.url()
            val url = originalHttpUrl.newBuilder().build()
            val request =
                original.newBuilder().header("Content-Type", "application/json")
                    .header("charset", "UTF-8")
                    .method(original.method(), original.body()).url(url).build()
            Log.v(
                "Service::URL::::",
                request.url().toString() + "::::Headers::::" + request.headers() + "::::" + bodyToString(request))
            chain.proceed(request)
        }
        val retrofit = Retrofit.Builder()
            .baseUrl(endPointURL)
            .client(httpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(APIServices::class.java).also { mAPIServices = it }
    }


    fun getArticlesData(limit: Int, pageLoad: Int): MutableLiveData<List<Articles>> {

        var data = MutableLiveData<List<Articles>>()
        val call = mAPIServices?.articlesListData(limit , pageLoad)

            call!!.enqueue(object : Callback<List<Articles>> {
                override fun onFailure(call: Call<List<Articles>>, t: Throwable?) {
                    data.setValue(null)
                    Log.v("retrofit", "call failed")
                }

                override fun onResponse(call: Call<List<Articles>>, response: Response<List<Articles>>) {
                    Log.e("Service Success Resp:::", Gson().toJson(response!!.body()))
                    data.setValue(response!!.body())
                }

            })

        return data
    }


    companion object {
        private var projectRepository: APIServiceCall? = null
        private val endPointURL: String? = null

        @get:Synchronized
        val instance: APIServiceCall?
            get() {
                if (projectRepository == null) {
                    projectRepository = APIServiceCall()
                }
                return projectRepository
            }

        private fun bodyToString(request: Request): String {
            val copy = request.newBuilder().build()
            return if (copy.body() != null) {
                try {
                    val buffer = Buffer()
                    copy.body()!!.writeTo(buffer)
                    buffer.readUtf8()
                } catch (e: IOException) {
                    "did not work"
                }
            } else {
                "No body founded"
            }
        }
    }
}



