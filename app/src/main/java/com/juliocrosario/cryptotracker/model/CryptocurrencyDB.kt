package com.juliocrosario.cryptotracker.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities =[Cryptocurrency::class], version = 1,exportSchema = false )
abstract class CryptocurrencyDB: RoomDatabase() {

    abstract fun cryptocurrencyDao(): CryptocurrencyDao

    companion object {
        @Volatile
        private var INSTANCE: CryptocurrencyDB? = null

        fun getDatabase(context: Context): CryptocurrencyDB{
           val tempInstance = INSTANCE
            if(tempInstance != null){
               return tempInstance
           }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CryptocurrencyDB::class.java,
                    "cryptocurrency_DB"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}