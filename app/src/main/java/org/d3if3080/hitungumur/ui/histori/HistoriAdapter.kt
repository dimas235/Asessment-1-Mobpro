package org.d3if3080.hitungumur.ui.histori

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

import org.d3if3080.hitungumur.databinding.ItemHistoriBinding
import org.d3if3080.hitungumur.db.UmurEntity
import org.d3if3080.hitungumur.model.hitungUmur
import java.text.SimpleDateFormat
import java.util.*

class HistoriAdapter : ListAdapter<UmurEntity, HistoriAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK =
            object : DiffUtil.ItemCallback<UmurEntity>() {
                override fun areItemsTheSame(
                    oldItem: UmurEntity, newItem: UmurEntity
                ) = oldItem.id == newItem.id

                override fun areContentsTheSame(
                    oldItem: UmurEntity, newItem: UmurEntity
                ) = oldItem == newItem
            }

    }


    class ViewHolder(
        private val binding: ItemHistoriBinding

    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: UmurEntity) = with(binding) {


                val umur = item.hitungUmur()
                val tahunLahir = item.tahunLahir
                val bulanLahir = item.bulanLahir
                val tanggalLahir = item.tanggalLahir

                val dateFormatter = SimpleDateFormat(
                    "dd MMMM yyyy",
                    Locale("id", "ID")
                )
                kategoriTextView.text = "U"
                tanggalTextView.text = dateFormatter.format(item.tanggal)
                dataTextView.text =
                    "Input: (tahun lahir = $tahunLahir, bulan lahir = $bulanLahir\ntanggal lahir = $tanggalLahir)\n" +
                            "Hasil: ${umur.tahunSekarang} tahun ${umur.bulanSekarang} bulan ${umur.tanggalSekarang} hari"


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemHistoriBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
