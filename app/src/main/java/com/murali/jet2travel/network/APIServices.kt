/**
 * Created by Murali Mohan on 13/10/2020.
 */

package com.murali.jet2travel.network

import Articles
import com.murali.jet2travel.utils.Constants
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface APIServices {
//page=1&limit=70
   /* @GET
    fun articlesListData(@Url url: String?): List<Articles>*/

    @GET("blogs?")
    fun articlesListData(@Query("page") page: Int, @Query("limit") pageLimit: Int): Call<List<Articles>>
}