import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.d3if3080.hitungumur.R
import org.d3if3080.hitungumur.model.ListUmur

class UmurFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: UmurAdapter
    private lateinit var umurList: List<ListUmur>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_item, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = UmurAdapter()
        recyclerView.adapter = adapter
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Panggil metode untuk memuat data dari JSON API
        loadUmurData()
    }

    private fun loadUmurData() {
        // Menggunakan data yang diberikan dalam pertanyaan sebagai contoh
        umurList = listOf(
            ListUmur("1 sampai 10", "Anak anak", "Anak"),
            ListUmur("11 sampai 25", "Remaja", "Remaja"),
            ListUmur("26 sampai 45", "Dewasa", "Dewasa"),
            ListUmur("45 sampai 65", "Lansia", "Lansia"),
            ListUmur("66 sampai 100", "Manula", "Manula")
        )
        // Mengirim data ke adapter
        adapter.setUmurList(umurList)
    }
}
