package org.d3if3080.hitungumur

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import org.d3if3080.hitungumur.databinding.FragmentLoadingBinding

class LoadingFragment : Fragment() {

    private lateinit var binding: FragmentLoadingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoadingBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.btnMulai.setOnClickListener {
            it.findNavController().navigate(R.id.action_loadingFragment_to_hitungFragment2)
        }
    }
}