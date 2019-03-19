package cafe.adriel.chucknorrisfacts.presentation.search

import cafe.adriel.chucknorrisfacts.presentation.BaseViewModel
import cafe.adriel.chucknorrisfacts.repository.fact.FactRepository
import cafe.adriel.chucknorrisfacts.repository.search.SearchRepository
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.zipWith

class SearchViewModel(
    val factRepository: FactRepository,
    val searchRepository: SearchRepository
) : BaseViewModel<SearchState>() {

    init {
        val categoriesSingle = factRepository.getCategories().map { it.shuffled().toSet() }
        val pastSearchesSingle = searchRepository.getPastSearches()

        disposables += categoriesSingle
            .zipWith(pastSearchesSingle)
            .subscribe({ result ->
                val state = SearchState(result.first, result.second)
                initState { state }
            }, { error ->
                error.printStackTrace()
                initState { SearchState() }
            })
    }

    fun saveQuery(query: String) {
        disposables += searchRepository.addSearchQuery(query)
    }

}