package br.com.dio.app.repositories.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Repo (
    val id: Long,
    val name: String,
    val owner: Owner,
    @SerializedName("stargazers_count")
    val stargazersCount: Long,
    @SerializedName("watchers_count")
    val watchersCount: Long,
    @SerializedName("forks_count")
    val forksCount: Long,
    val language: String?,
    @SerializedName("html_url")
    val htmlURL: String,
    val description: String?
) : Parcelable