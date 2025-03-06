package com.fdlr.omieteste.data.datasouce.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.fdlr.omieteste.data.dao.CatalogDao
import com.fdlr.omieteste.data.dao.OrderDao
import com.fdlr.omieteste.data.datasouce.local.converters.Converters
import com.fdlr.omieteste.data.model.CatalogItemEntity
import com.fdlr.omieteste.data.model.OrderEntity
import com.fdlr.omieteste.presentation.utils.Constants.DATABASE_NAME

@TypeConverters(Converters::class)
@Database(
    entities = [
        OrderEntity::class,
        CatalogItemEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun orderDao(): OrderDao
    abstract fun catalogDao(): CatalogDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
