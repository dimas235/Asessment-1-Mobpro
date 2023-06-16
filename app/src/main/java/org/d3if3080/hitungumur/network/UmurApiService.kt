package org.d3if3080.hitungumur.network

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://raw.githubusercontent.com/dimas235/Asessment-1-Mobpro/APIUmur/"
private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()
interface UmurApiService {
    @GET("umur.json")
    suspend fun getUmur(): String
}
object UmurApi {
    val service: UmurApiService by lazy {
        retrofit.create(UmurApiService::class.java)
    }

    fun getImageUrl(Gambar: String): String {
        return "$BASE_URL/$Gambar.jpg"
    }
}