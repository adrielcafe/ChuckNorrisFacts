package cafe.adriel.chucknorrisfacts.presentation.search

import cafe.adriel.chucknorrisfacts.presentation.BaseViewModel
import cafe.adriel.chucknorrisfacts.repository.fact.FactRepository
import cafe.adriel.chucknorrisfacts.repository.search.SearchRepository
import io.reactivex.rxkotlin.plusAssign

class SearchViewModel(
    val factRepository: FactRepository,
    val searchRepository: SearchRepository
) : BaseViewModel<SearchState>() {

    init {
        initState { SearchState() }
        loadSuggestions()
        loadPastSearches()
    }

    private fun loadSuggestions(){
        disposables += factRepository.getCategories()
            .subscribe({ categories ->
                updateState { it.copy(suggestions = categories) }
            }, { error ->
                error.printStackTrace()
                updateState { it.copy(error = error.localizedMessage) }
            })
    }

    private fun loadPastSearches(){
        disposables += searchRepository.getPastSearches()
            .subscribe({ pastSearches ->
                updateState { it.copy(pastSearches = pastSearches) }
            }, { error ->
                error.printStackTrace()
                updateState { it.copy(error = error.localizedMessage) }
            })
    }

    fun saveQuery(query: String) {
        disposables += searchRepository.addSearchQuery(query)
    }

    fun formatQuery(query: String) = query.toLowerCase().trim()

}