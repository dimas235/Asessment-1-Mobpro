package org.d3if3080.hitungumur.model

import org.d3if3080.hitungumur.db.UmurEntity
import java.util.*

fun UmurEntity.hitungUmur(): Hasil {
    val cal = Calendar.getInstance()
    val tahunSekarang = cal.get(Calendar.YEAR)
    val bulanSekarang = cal.get(Calendar.MONTH) + 1
    val tanggalSekarang = cal.get(Calendar.DATE)

    var umurTahun = tahunSekarang - tahunLahir
    var umurBulan = bulanSekarang - bulanLahir
    val umurTanggal = tanggalSekarang - tanggalLahir

    return Hasil(umurTahun, umurBulan, umurTanggal)
}