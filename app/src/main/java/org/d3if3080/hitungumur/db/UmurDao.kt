package org.d3if3080.hitungumur.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UmurDao {

    @Insert
    fun insert(umur: UmurEntity)

    @Query("SELECT * FROM umur ORDER BY id DESC")
    fun getLastUmur(): LiveData<List<UmurEntity>>

    @Query("DELETE FROM umur")
    fun clearData()
}