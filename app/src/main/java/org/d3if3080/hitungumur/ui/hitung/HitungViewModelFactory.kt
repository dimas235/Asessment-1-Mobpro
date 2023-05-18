package org.d3if3080.hitungumur.ui.hitung

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.d3if3080.hitungumur.db.UmurDao

class HitungViewModelFactory (
    private val db: UmurDao
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(HitungUmurViewModel::class.java)) {
            return HitungUmurViewModel(db) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}