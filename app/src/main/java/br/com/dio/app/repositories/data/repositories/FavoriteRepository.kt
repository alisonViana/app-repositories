package br.com.dio.app.repositories.data.repositories

import android.util.Log
import br.com.dio.app.repositories.data.model.Favorite
import br.com.dio.app.repositories.data.services.FavoriteDao

class FavoriteRepository(private val dao: FavoriteDao) {

    fun getAll() = dao.getAll()

    suspend fun insert(favorite: Favorite) {
            try {
                dao.insert(favorite)
                Log.i("koindb", "insert successes")
            } catch (ex: Exception) {
                Log.i("koindb", "insert error")}
    }

    suspend fun delete(favorite: Favorite) {
        try {
                dao.delete(favorite)
            } catch (ex: Exception) {ex.printStackTrace()}
        }

}