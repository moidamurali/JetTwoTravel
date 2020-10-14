/**
 * Created by Murali Mohan on 13/10/2020.
 */

package com.murali.jet2travel.network

import Articles
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.murali.jet2travel.utils.Constants
import okhttp3.Cache
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
    val cacheSize = (5 * 1024 * 1024).toLong()

    fun hitServiceServiceCall(context: Context): APIServices {
        val cache = Cache(context.cacheDir, cacheSize)

        val httpClient = OkHttpClient.Builder().cache(cache)

        httpClient.addInterceptor { chain ->
                var request = chain.request()
                request = if (Constants.hasNetworkConnected(context)!!) {
                    request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build()
                }
                else {
                    request.newBuilder().header("Cache-Control","public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7 ).build()
                }


            Log.v("Service::URL::::", request.url().toString() + "::::Headers::::" + request.headers() + "::::" + bodyToString(request))
                chain.proceed(request)
            }
            .build()

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



