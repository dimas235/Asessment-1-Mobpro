package org.d3if3080.hitungumur.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "umur")
data class UmurEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var tanggal : Long = System.currentTimeMillis(),
    var tahunLahir: Int,
    var bulanLahir: Int,
    var tanggalLahir: Int,
)