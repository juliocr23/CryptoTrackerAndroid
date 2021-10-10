package com.juliocrosario.cryptotracker.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CryptocurrencyDao {

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun addCryptocurrency(cryptocurrency: Cryptocurrency)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCryptos(list: List<Cryptocurrency>)

    @Query("SELECT * FROM Cryptocurrency")
    suspend fun readAllData(): List<Cryptocurrency>
}