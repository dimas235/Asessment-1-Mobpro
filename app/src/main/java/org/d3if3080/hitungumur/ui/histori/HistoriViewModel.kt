package org.d3if3080.hitungumur.ui.histori

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.d3if3080.hitungumur.db.UmurDao

class HistoriViewModel(private val db: UmurDao) : ViewModel() {
    val data = db.getLastUmur()

    fun hapusData() = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            db.clearData()
        }
    }


}