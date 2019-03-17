package cafe.adriel.chucknorrisfacts.repository.search

import com.pacoworks.rxpaper2.RxPaperBook
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SearchRepository(private val preferences: RxPaperBook) {

    companion object {
        private const val PREF_SEARCH_QUERIES = "searchQueries"
        private const val MAX_SEARCH_TERMS = 5
    }

    fun getSearchQueries() =
        preferences.read<List<String>>(PREF_SEARCH_QUERIES, emptyList())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun addSearchQuery(term: String) =
        getSearchQueries()
            .map { terms ->
                // Add the new term to the top and only return the first 5 elements
                terms.toMutableList()
                    .also { it.add(0, term) }
                    .take(MAX_SEARCH_TERMS)
            }
            .doOnSuccess { terms ->
                preferences.write(PREF_SEARCH_QUERIES, terms).blockingAwait()
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
}