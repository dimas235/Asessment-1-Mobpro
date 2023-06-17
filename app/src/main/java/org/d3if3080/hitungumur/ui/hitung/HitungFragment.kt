package org.d3if3080.hitungumur.ui.hitung

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import org.d3if3080.hitungumur.R
import org.d3if3080.hitungumur.databinding.FragmentHitungBinding
import org.d3if3080.hitungumur.db.UmurDb
import org.d3if3080.hitungumur.model.Hasil
import java.util.*

class HitungFragment : Fragment(){

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.options_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_histori -> {
                findNavController().navigate(R.id.action_hitungFragment_to_historiFragment)
                return true
            }

            R.id.menu_about -> {
                findNavController().navigate(R.id.action_hitungFragment_to_aboutFragment)
                return true
            }
        }
            return super.onOptionsItemSelected(item)
        }

    private lateinit var binding: FragmentHitungBinding
    private val viewModel: HitungUmurViewModel by lazy {
        val db = UmurDb.getInstance(requireContext())
        val factory = HitungViewModelFactory(db.dao)
        ViewModelProvider(this, factory)[HitungUmurViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHitungBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)
        return binding.root

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.btnHitung.setOnClickListener { hitungUmur() }
        binding.bagikan.setOnClickListener { shareData() }
        viewModel.getHasil().observe(requireActivity()) { showResult(it) }
    }

    private fun shareData() {
        val message = binding.tvHasil.text.toString()
        binding.edtTahunLahir.text.toString()
        binding.edtBulanLahir.text.toString()
        binding.edtTanggalLahir.text.toString()

        binding.tvHasil.text = message
        val shareIntent = Intent (Intent.ACTION_SEND)
        shareIntent.setType("text/plain").putExtra(Intent.EXTRA_TEXT, message)
        if (shareIntent.resolveActivity(requireActivity().packageManager) != null) {
            startActivity(shareIntent)
        }
    }

    private fun hitungUmur() {
        val tahunLahir = binding.edtTahunLahir.text.toString()
        val bulanLahir = binding.edtBulanLahir.text.toString()
        val tanggalLahir = binding.edtTanggalLahir.text.toString()

        if (TextUtils.isEmpty(tahunLahir) || TextUtils.isEmpty(bulanLahir) || TextUtils.isEmpty(tanggalLahir)) {
            binding.tvHasil.text = "Data tidak boleh kosong"
        } else {
            val cal = Calendar.getInstance()
            val tahunSekarang = cal.get(Calendar.YEAR)
            val bulanSekarang = cal.get(Calendar.MONTH) + 1
            val tanggalSekarang = cal.get(Calendar.DATE)

            val tahunLahirInt = tahunLahir.toInt()
            val bulanLahirInt = bulanLahir.toInt()
            val tanggalLahirInt = tanggalLahir.toInt()

            if (tahunLahirInt > tahunSekarang || (tahunLahirInt == tahunSekarang && bulanLahirInt > bulanSekarang)) {
                binding.tvHasil.text = "Tahun lahir tidak valid"
            } else if (tahunLahirInt == tahunSekarang && bulanLahirInt > bulanSekarang || bulanLahirInt > 12) {
                binding.tvHasil.text = "Bulan lahir tidak valid"
            } else if (tahunLahirInt == tahunSekarang && bulanLahirInt == bulanSekarang || tanggalLahirInt > 31) {
                binding.tvHasil.text = "Tanggal lahir tidak valid"
            } else {
                viewModel.umur(tahunLahirInt, bulanLahirInt, tanggalLahirInt)
            }
        }
    }

//    private fun hitungUmur() {
//        val tahunLahir = binding.edtTahunLahir.text.toString()
//        val bulanLahir = binding.edtBulanLahir.text.toString()
//        val tanggalLahir = binding.edtTanggalLahir.text.toString()
//        val cal = Calendar.getInstance()
//        val tahunSekarang = cal.get(Calendar.YEAR)
//        val bulanSekarang = cal.get(Calendar.MONTH) + 1
//        val tanggalSekarang = cal.get(Calendar.DATE)
//        val tahunLahirInt = tahunLahir.toInt()
//        val bulanLahirInt = bulanLahir.toInt()
//        val tanggalLahirInt = tanggalLahir.toInt()
//
//        if (TextUtils.isEmpty(tahunLahir) || TextUtils.isEmpty(bulanLahir) || TextUtils.isEmpty(
//                tanggalLahir
//            )
//        ) {
//            binding.tvHasil.text = "Data tidak boleh kosong"
//
//        } else if (tahunLahirInt > tahunSekarang || (tahunLahirInt == tahunSekarang && bulanLahirInt > bulanSekarang)) {
//            binding.tvHasil.text = "Tahun lahir tidak valid"
//        } else if (tahunLahirInt == tahunSekarang && bulanLahirInt > bulanSekarang || bulanLahirInt > 12) {
//            binding.tvHasil.text = "Bulan lahir tidak valid"
//        } else if (tahunLahirInt == tahunSekarang && bulanLahirInt == bulanSekarang || tanggalLahirInt > tanggalSekarang || tanggalLahirInt > 31) {
//            binding.tvHasil.text = "Tanggal lahir tidak valid"
//        } else {
//            viewModel.umur(tahunLahirInt, bulanLahirInt, tanggalLahirInt)
//        }
//    }

    private fun showResult(result: Hasil) {
        if (result == null) return
        binding.tvHasil.text = "Umur kamu adalah ${result.tahunSekarang} tahun ${result.bulanSekarang} bulan ${result.tanggalSekarang} hari"
    }
}
