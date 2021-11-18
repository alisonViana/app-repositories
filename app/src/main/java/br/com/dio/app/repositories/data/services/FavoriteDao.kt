package br.com.dio.app.repositories.data.services

import androidx.lifecycle.LiveData
import androidx.room.*
import br.com.dio.app.repositories.data.model.Favorite

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM Favorite")
    fun getAll(): LiveData<List<Favorite>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(favorite: Favorite)

    @Delete
    suspend fun delete(favorite: Favorite)
}