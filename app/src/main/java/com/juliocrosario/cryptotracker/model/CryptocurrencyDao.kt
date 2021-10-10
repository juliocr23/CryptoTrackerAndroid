package com.juliocrosario.cryptotracker.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CryptocurrencyDao {

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun addCryptocurrency(cryptocurrency: Cryptocurrency)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCryptos(list: List<Cryptocurrency>)

    @Update
    suspend fun updateCryptos(list: List<Cryptocurrency>)

    @Delete
    suspend fun deleteCrypto(cryptocurrency: Cryptocurrency)

    @Query("DELETE FROM Cryptocurrency")
    suspend fun deleteAll()

    @Query("SELECT * FROM Cryptocurrency")
    suspend fun readAllData(): List<Cryptocurrency>
}