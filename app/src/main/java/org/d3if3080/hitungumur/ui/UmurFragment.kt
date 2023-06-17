import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.d3if3080.hitungumur.R
import org.d3if3080.hitungumur.databinding.FragmentHitungBinding
import org.d3if3080.hitungumur.databinding.FragmentItemBinding
import org.d3if3080.hitungumur.db.UmurDb
import org.d3if3080.hitungumur.model.ListUmur
import org.d3if3080.hitungumur.network.ApiStatus
import org.d3if3080.hitungumur.network.UmurApi
import org.d3if3080.hitungumur.ui.histori.HistoriViewModel
import org.d3if3080.hitungumur.ui.histori.HistoriViewModelFactory
import org.d3if3080.hitungumur.ui.hitung.HitungUmurViewModel
import org.d3if3080.hitungumur.ui.hitung.HitungViewModelFactory
import org.d3if3080.hitungumur.view.MainActivity


class UmurFragment : Fragment() {


    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: UmurAdapter
    private var umurList: List<ListUmur> = emptyList()
    private lateinit var binding: FragmentItemBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentItemBinding.inflate(inflater, container, false)
        val view = inflater.inflate(R.layout.fragment_item, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = UmurAdapter()
        recyclerView.adapter = adapter
        return view
    }


    private val viewModel: HitungUmurViewModel by lazy {
        val db = UmurDb.getInstance(requireContext())
        val factory = HitungViewModelFactory(db.dao)
        ViewModelProvider(this, factory)[HitungUmurViewModel::class.java]
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getStatus().observe(viewLifecycleOwner) {
            updateProgress(it)
        }
        viewModel.scheduleUpdater(requireActivity().application)

        val apiService = UmurApi.service
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = apiService.getUmur()
                if (response.isSuccessful) {
                    val umurData = response.body()
                    if (umurData != null) {
                        umurList = umurData
                        adapter.setUmurList(umurList)
                    }
                } else {
                    // Handle error response
                }
            } catch (e: Exception) {
                // Handle exception
            }
        }
    }

    private fun updateProgress(status: ApiStatus) {
        when (status) {
            ApiStatus.LOADING -> {
                binding.progressBar.visibility = View.VISIBLE
            }
            ApiStatus.SUCCESS -> {
                binding.progressBar.visibility = View.GONE
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    requestNotificationPermission()
                }
            }
            ApiStatus.FAILED -> {
                binding.progressBar.visibility = View.GONE
                binding.networkError.visibility = View.VISIBLE
            }
        }
    }
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun requestNotificationPermission() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                MainActivity.PERMISSION_REQUEST_CODE
            )
        }
    }


}
