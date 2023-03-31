package org.d3if3080.hitungumur

import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.d3if3080.hitungumur.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var edtTahunLahir: EditText
    private lateinit var edtBulanLahir: EditText
    private lateinit var edtTanggalLahir: EditText
    private lateinit var btnHitung: Button
    private lateinit var tvHasil: TextView
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        edtTahunLahir = binding.edtTahunLahir!!
        edtBulanLahir = binding.edtBulanLahir!!
        edtTanggalLahir = binding.edtTanggalLahir!!
        btnHitung = binding.btnHitung!!
        tvHasil = binding.tvHasil!!

        btnHitung.setOnClickListener {
            val tahunLahir = edtTahunLahir.text.toString()
            val bulanLahir = edtBulanLahir.text.toString()
            val tanggalLahir = edtTanggalLahir.text.toString()

            if (TextUtils.isEmpty(tahunLahir) || TextUtils.isEmpty(bulanLahir) || TextUtils.isEmpty(
                    tanggalLahir
                )
            ) {
                tvHasil.text = "Data tidak boleh kosong"
            } else {
                val tahunLahirInt = tahunLahir.toInt()
                val bulanLahirInt = bulanLahir.toInt()
                val tanggalLahirInt = tanggalLahir.toInt()

                val cal = Calendar.getInstance()
                val tahunSekarang = cal.get(Calendar.YEAR)
                val bulanSekarang = cal.get(Calendar.MONTH) + 1
                val tanggalSekarang = cal.get(Calendar.DATE)

                if (tahunLahirInt > tahunSekarang || tahunLahirInt == tahunSekarang && bulanLahirInt > bulanSekarang) {
                    tvHasil.text = "Tahun lahir tidak valid"
                } else if (tahunLahirInt == tahunSekarang && bulanLahirInt > bulanSekarang || bulanLahirInt > 12) {
                    tvHasil.text = "Bulan lahir tidak valid"
                } else if (tahunLahirInt == tahunSekarang && bulanLahirInt == bulanSekarang || tanggalLahirInt > 31) {
                    tvHasil.text = "Tanggal lahir tidak valid"
                } else {
                    var umurTahun = tahunSekarang - tahunLahirInt
                    var umurBulan = bulanSekarang - bulanLahirInt
                    var umurTanggal = tanggalSekarang - tanggalLahirInt

                    if (umurBulan < 0) {
                        umurTahun--
                        umurBulan += 12
                    }

                    tvHasil.text =
                        "Umur kamu adalah $umurTahun tahun $umurBulan bulan $umurTanggal hari"
                }
            }
        }
    }
}
