package cafe.adriel.chucknorrisfacts.repository.search

import com.pacoworks.rxpaper2.RxPaperBook
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SearchRepository(private val preferences: RxPaperBook) {

    companion object {
        private const val PREF_PAST_SEARCHES = "pastSearches"
        private const val MAX_SEARCH_QUERIES = 8
    }

    fun getPastSearches() =
        preferences.read<List<String>>(PREF_PAST_SEARCHES, emptyList())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun addSearchQuery(query: String) =
        getPastSearches()
            .map { terms ->
                terms.toMutableList()
                    .also {
                        // Remove old query, if it exists, to avoid duplicates
                        if(it.contains(query)){
                            it.removeAll { it == query }
                        }
                        // Add the new term to the top
                        it.add(0, query)
                    }
                    .take(MAX_SEARCH_QUERIES)
            }
            .doOnSuccess { terms ->
                preferences.write(PREF_PAST_SEARCHES, terms).blockingAwait()
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
}