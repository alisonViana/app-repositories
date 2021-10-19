package br.com.dio.app.repositories.data.repositories

import br.com.dio.app.repositories.core.RemoteException
import br.com.dio.app.repositories.data.services.GitHubService
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class RepoRepository(private val service: GitHubService) : RepoRepositoryInterface{

    override suspend fun listRepositories(user: String) = flow {
        try {
            val listRepo = service.listRepos(user)
            emit(listRepo)
        } catch (ex: HttpException) {
            throw  RemoteException(ex.message)
        }
    }
}