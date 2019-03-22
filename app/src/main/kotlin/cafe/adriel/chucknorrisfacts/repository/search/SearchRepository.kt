package cafe.adriel.chucknorrisfacts.repository.search

import com.pacoworks.rxpaper2.RxPaperBook

class SearchRepository(private val preferences: RxPaperBook) {

    companion object {
        const val PREF_PAST_SEARCHES = "pastSearches"
        const val MAX_SEARCH_QUERIES = 8
    }

    fun getPastSearches() =
        preferences.read<List<String>>(PREF_PAST_SEARCHES, emptyList())

    fun addSearchQuery(query: String) =
        getPastSearches()
            .map { terms ->
                terms.toMutableList()
                    .also {
                        // Remove old query, if it exists, to prevent duplicates
                        if (it.contains(query)) {
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
            .map { Unit }
}
