package org.d3if3080.hitungumur.ui.histori

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.d3if3080.hitungumur.R
import org.d3if3080.hitungumur.databinding.FragmentHistoriBinding
import org.d3if3080.hitungumur.db.UmurDb

class HistoriFragment : Fragment() {

    private lateinit var myAdapter: HistoriAdapter
    private lateinit var binding: FragmentHistoriBinding

    private val viewModel: HistoriViewModel by lazy {
        val db = UmurDb.getInstance(requireContext())
        val factory = HistoriViewModelFactory(db.dao)
        ViewModelProvider(this, factory)[HistoriViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoriBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        myAdapter = HistoriAdapter()
        with(binding.recyclerView) {
            adapter = myAdapter
            setHasFixedSize(true)
        }

        viewModel.data.observe(viewLifecycleOwner, {binding.emptyView.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
            myAdapter.submitList(it)
        })

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.histori_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_hapus) {
            hapusData()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun hapusData() {
        MaterialAlertDialogBuilder(requireContext())
            .setMessage("Apakah Anda yakin ingin menghapus semua data?")
            .setPositiveButton(R.string.hapus) { _, _ ->
                viewModel.hapusData()
            }
            .setNegativeButton(R.string.batal) { dialog, _ ->
                dialog.cancel()
            }
            .show()
    }

}