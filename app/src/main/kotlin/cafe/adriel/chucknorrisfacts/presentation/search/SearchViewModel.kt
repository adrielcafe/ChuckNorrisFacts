package cafe.adriel.chucknorrisfacts.presentation.search

import cafe.adriel.chucknorrisfacts.presentation.BaseViewModel
import cafe.adriel.chucknorrisfacts.repository.facts.FactsRepository
import cafe.adriel.chucknorrisfacts.repository.search.SearchRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers

class SearchViewModel(
    val factsRepository: FactsRepository,
    val searchRepository: SearchRepository
) : BaseViewModel<SearchViewState>() {

    init {
        initState { SearchViewState() }
        loadSuggestions()
        loadPastSearches()
    }

    fun saveQuery(query: String) {
        disposables += searchRepository.addSearchQuery(query)
            .doOnError { it.printStackTrace() }
            .subscribe()
    }

    fun formatQuery(query: String) = query.toLowerCase().trim()

    private fun loadSuggestions() {
        disposables += factsRepository.getCategories()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                updateState { it.copy(suggestions = result) }
            }, ::handleError)
    }

    private fun loadPastSearches() {
        disposables += searchRepository.getPastSearches()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                updateState { it.copy(pastSearches = result) }
            }, ::handleError)
    }

    private fun handleError(error: Throwable) {
        error.printStackTrace()
    }
}
