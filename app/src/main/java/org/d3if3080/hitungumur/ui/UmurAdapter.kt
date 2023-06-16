import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import org.d3if3080.hitungumur.R
import org.d3if3080.hitungumur.model.ListUmur
import org.d3if3080.hitungumur.network.UmurApi

class UmurAdapter : RecyclerView.Adapter<UmurAdapter.UmurViewHolder>() {

    private var umurList: List<ListUmur> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UmurViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return UmurViewHolder(view)
    }

    override fun onBindViewHolder(holder: UmurViewHolder, position: Int) {
        val umur = umurList[position]
        holder.bind(umur)
    }

    override fun getItemCount(): Int = umurList.size

    fun setUmurList(list: List<ListUmur>) {
        umurList = list
        notifyDataSetChanged()
    }

    inner class UmurViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val umurTextView: TextView = itemView.findViewById(R.id.umurTextView)
        private val kategoriTextView: TextView = itemView.findViewById(R.id.kategoriTextView)
        private val imageView: ImageView = itemView.findViewById(R.id.imageView)

        fun bind(umur: ListUmur) {
            umurTextView.text = umur.Umur
            kategoriTextView.text = umur.Kategori
            // Menggunakan Glide untuk memuat gambar dari URL
            Glide.with(itemView)
                .load(UmurApi.getImageUrl(umur.Gambar))
                .placeholder(R.drawable.ic_baseline_broken_image_24)
                .into(imageView)
        }

        private fun getImageUrl(gambarId: String): String {
            // Mengembalikan URL gambar berdasarkan ID gambar
            // Misalnya, Anda dapat menggunakan nama file dari res/drawable sebagai URL gambar
            return "https://example.com/images/$gambarId.png"
        }
    }
}
