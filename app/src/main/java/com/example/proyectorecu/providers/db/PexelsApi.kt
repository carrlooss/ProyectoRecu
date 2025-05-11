package com.example.proyectorecu.providers.db

import com.example.proyectorecu.models.PexelsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface PexelsApi {
    @Headers("Authorization: mka1wKorBsVNjv1nLe2EPIr2WsekMEXLuA5fmHpALYM1hbzmXa213t2G")
    @GET("v1/curated")
    fun getCuratedPhotos(
        @Query("per_page") perPage: Int = 10,
        @Query("page") page: Int = 1
    ): Call<PexelsResponse>
}