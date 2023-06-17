package org.d3if3080.hitungumur.network


import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.d3if3080.hitungumur.model.ListUmur
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.Response


private const val BASE_URL = "https://raw.githubusercontent.com/dimas235/Asessment-1-Mobpro/APIUmur/"
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()
interface UmurApiService {
    @GET("umur.json")
    suspend fun getUmur(): Response<List<ListUmur>>
}
object UmurApi {
    val service: UmurApiService by lazy {
        retrofit.create(UmurApiService::class.java)
    }

    fun getImageUrl(gambar: String): String {
        return "$BASE_URL/$gambar.jpg"
    }
}
enum class ApiStatus { LOADING, SUCCESS, FAILED }