package br.com.dio.app.repositories.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Favorite(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userName: String,
    val userAvatar: String
)
