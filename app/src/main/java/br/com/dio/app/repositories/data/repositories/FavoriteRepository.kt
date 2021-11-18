package br.com.dio.app.repositories.data.repositories

import android.util.Log
import br.com.dio.app.repositories.data.model.Favorite
import br.com.dio.app.repositories.data.services.FavoriteDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class FavoriteRepository(private val dao: FavoriteDao) {

    fun getAll() = runBlocking {
        dao.getAll()
    }

    fun insert(favorite: Favorite) = runBlocking {
        launch(Dispatchers.IO) {
            try {
                dao.insert(favorite)
                Log.i("koindb", "insert successes")
            }catch (ex: Exception) {
                Log.i("koindb", "insert error")}
        }
    }

    fun delete(favorite: Favorite) = runBlocking {
        launch(Dispatchers.IO) {
            try {
                dao.delete(favorite)
            }catch (ex: Exception) {ex.printStackTrace()}
        }
    }

}