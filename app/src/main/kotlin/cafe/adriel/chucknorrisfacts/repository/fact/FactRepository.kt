package cafe.adriel.chucknorrisfacts.repository.fact

import com.pacoworks.rxpaper2.RxPaperBook

class FactRepository(private val factService: FactService, private val preferences: RxPaperBook) {

    companion object {
        private const val PREF_FACT_CATEGORIES = "factCategories"
        private const val MAX_CATEGORIES = 8
    }

    fun getFacts(query: String) =
        factService.getFacts(query)
            .map { it.result }

    fun getCategories() =
        getLocalCategories()
            .map { categories ->
                if (categories.isEmpty()) {
                    getRemoteCategories().blockingGet()
                } else {
                    categories
                }
            }
            .map { categories ->
                // Select 8 random categories
                categories.shuffled().take(MAX_CATEGORIES).toSet()
            }

    private fun getLocalCategories() =
        preferences.read<Set<String>>(PREF_FACT_CATEGORIES, emptySet())

    private fun getRemoteCategories() =
        factService.getCategories()
            .doOnSuccess {
                // Cache the result
                preferences.write(PREF_FACT_CATEGORIES, it).blockingAwait()
            }
}
