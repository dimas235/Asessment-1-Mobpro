package org.d3if3080.hitungumur.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UmurEntity::class], version = 1, exportSchema = false)
abstract class UmurDb : RoomDatabase() {
    abstract val dao: UmurDao

    companion object {

        @Volatile
        private var INSTANCE: UmurDb? = null

        fun getInstance(context: Context): UmurDb {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        UmurDb::class.java,
                        "umur.db"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}