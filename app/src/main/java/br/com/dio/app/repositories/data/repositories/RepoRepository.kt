package br.com.dio.app.repositories.data.repositories

import br.com.dio.app.repositories.data.services.GitHubService
import kotlinx.coroutines.flow.flow

class RepoRepository(private val service: GitHubService) : RepoRepositoryInterface{

    override suspend fun listRepositories(user: String) = flow {
        val listRepo = service.listRepos(user)
        emit(listRepo)
    }
}