package org.d3if3080.hitungumur.ui.histori

import androidx.lifecycle.ViewModel
import org.d3if3080.hitungumur.db.UmurDao

class HistoriViewModel(db: UmurDao) : ViewModel(){
    val data = db.getLastUmur()
}