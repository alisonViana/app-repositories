package br.com.dio.app.repositories.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.dio.app.repositories.data.model.Favorite
import br.com.dio.app.repositories.data.model.Owner
import br.com.dio.app.repositories.data.model.Repo
import br.com.dio.app.repositories.data.repositories.FavoriteRepository
import br.com.dio.app.repositories.domain.ListUserRepositoriesUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class MainViewModel(
    private val listUserRepositoriesUseCase: ListUserRepositoriesUseCase,
    private val favoriteRepository: FavoriteRepository
) : ViewModel(){

    private var favoriteListSize: Int = 0
    private val _repos = MutableLiveData<State>()
    val repos: LiveData<State> = _repos

    fun getRepoList(user: String) {
        viewModelScope.launch {
            listUserRepositoriesUseCase.execute(user)
                .onStart {
                    _repos.postValue(State.Loading)
                }
                .catch {
                    _repos.postValue(State.Error(it))
                }
                .collect {
                    _repos.postValue(State.Success(it))
                }
        }
    }

    sealed class State {
        object Loading: State()
        data class Success(val list: List<Repo>): State()
        data class Error(val error: Throwable): State()
    }


    fun getFavoriteList(): LiveData<List<Favorite>>{
        return favoriteRepository.getAll()
    }

    fun addFavorite(owner: Owner) {
        favoriteRepository.insert(Favorite(userName = owner.login, userAvatar = owner.avatarURL))
    }

    fun removeFavorite(favorite: Favorite) {
        favoriteRepository.delete(favorite)
    }

    fun setFavoriteListSize(size: Int) {
        favoriteListSize = size
    }

    fun getFavoriteListSize(): Int = favoriteListSize

}