package org.d3if3080.hitungumur.ui.hitung

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
import org.d3if3080.hitungumur.model.Umur
import java.util.*

class HitungUmurViewModel(private val db: UmurDao) : ViewModel() {

    private val hasil = MutableLiveData<Hasil>()

    val data = db.getLastUmur()

    fun perhitungan (tahunLahir: Int, bulanLahir: Int, tanggalLahir: Int) {

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
        hasil.value = Hasil(umurTahun, umurBulan, umurTanggal)

        viewModelScope.launch {
            withContext(Dispatchers.IO){
            val dataUmur = UmurEntity(
                    tahunLahir = tahunLahir,
                    bulanLahir = bulanLahir,
                    tanggalLahir = tanggalLahir
                )
                db.insert(dataUmur)
            }
        }
    }

    fun umur( tahunLahir: Int, bulanLahir: Int, tanggalLahir: Int) {
        var umur = Umur(tahunLahir, bulanLahir, tanggalLahir)
        var tahunLahir = umur.tahunLahir
        var bulanLahir = umur.bulanLahir
        var tanggalLahir = umur.tanggalLahir

        perhitungan(tahunLahir, bulanLahir, tanggalLahir)

//
//        val hasilUmur = "Umur kamu adalah $umurTahun tahun $umurBulan bulan $umurTanggal hari"
//        umur.value = hasilUmur
    }

    fun getHasil(): LiveData<Hasil> = hasil
}
