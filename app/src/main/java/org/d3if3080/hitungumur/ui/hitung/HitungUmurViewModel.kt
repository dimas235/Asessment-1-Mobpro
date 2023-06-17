package org.d3if3080.hitungumur.ui.hitung

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.d3if3080.hitungumur.db.UmurDao
import org.d3if3080.hitungumur.db.UmurEntity
import org.d3if3080.hitungumur.model.Hasil
import org.d3if3080.hitungumur.model.ListUmur
import org.d3if3080.hitungumur.model.Umur
import org.d3if3080.hitungumur.network.ApiStatus
import org.d3if3080.hitungumur.network.UmurApi
import retrofit2.Response
import java.util.*

class HitungUmurViewModel(private val db: UmurDao) : ViewModel() {

    private val status = MutableLiveData<ApiStatus>()
    private val hasil = MutableLiveData<Hasil>()
    private val data = MutableLiveData<Response<List<ListUmur>>>()
    init {
        retrieveData()
    }

    private fun retrieveData() {
        viewModelScope.launch(Dispatchers.IO) {
            status.postValue(ApiStatus.LOADING)
            try {
                val response = UmurApi.service.getUmur()
                data.postValue(response)
                status.postValue(ApiStatus.SUCCESS)
            } catch (e: Exception) {
                Log.d("MainViewModel", "Failure: ${e.message}")
                status.postValue(ApiStatus.FAILED)
            }
        }
    }

    fun perhitungan(tahunLahir: Int, bulanLahir: Int, tanggalLahir: Int) {
        val cal = Calendar.getInstance()
        val tahunSekarang = cal.get(Calendar.YEAR)
        val bulanSekarang = cal.get(Calendar.MONTH) + 1
        val tanggalSekarang = cal.get(Calendar.DATE)

        var umurTahun = tahunSekarang - tahunLahir
        var umurBulan = bulanSekarang - bulanLahir
        val umurTanggal = tanggalSekarang - tanggalLahir

        if (umurBulan < 0) {
            umurTahun--
            umurBulan += 12
        }

        val dataUmur = UmurEntity(
            tahunLahir = tahunLahir,
            bulanLahir = bulanLahir,
            tanggalLahir = tanggalLahir,
        )

        hasil.value = Hasil(umurTahun, umurBulan, umurTanggal)

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                db.insert(dataUmur)
            }
        }
    }

    fun umur(tahunLahir: Int, bulanLahir: Int, tanggalLahir: Int) {
        var umur = Umur(tahunLahir, bulanLahir, tanggalLahir)
        var tahunLahir = umur.tahunLahir
        var bulanLahir = umur.bulanLahir
        var tanggalLahir = umur.tanggalLahir

        perhitungan(tahunLahir, bulanLahir, tanggalLahir)
    }

    fun getHasil(): LiveData<Hasil> = hasil
    fun getStatus(): LiveData<ApiStatus> = status

}
