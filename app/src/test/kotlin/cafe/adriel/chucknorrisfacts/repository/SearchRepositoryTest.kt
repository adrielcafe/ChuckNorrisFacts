package cafe.adriel.chucknorrisfacts.repository

import cafe.adriel.chucknorrisfacts.BaseTest
import cafe.adriel.chucknorrisfacts.repository.search.SearchRepository
import com.pacoworks.rxpaper2.RxPaperBook
import org.junit.Test
import org.koin.test.inject
import strikt.api.expectThat
import strikt.assertions.first
import strikt.assertions.hasSize
import strikt.assertions.isEmpty
import strikt.assertions.isEqualTo
import strikt.assertions.isNotEmpty

class SearchRepositoryTest : BaseTest() {
    private val searchRepository by inject<SearchRepository>()
    private val preferences by inject<RxPaperBook>()

    @Test
    fun addSearchQuery_addSameQueryTwice_returnListWithoutDuplicates() {
        val query = "test"
        repeat(2) {
            searchRepository.addSearchQuery(query).blockingGet()
        }

        val pastSearches = searchRepository.getPastSearches().blockingGet()
        val queryCount = pastSearches.count { it == query }
        expectThat(queryCount).isEqualTo(1)
    }

    @Test
    fun addSearchQuery_addMultipleQueries_lastSearchedQueryIsFirstInTheList() {
        val queries = listOf("test1", "test2", "test3")
        queries.forEach {
            searchRepository.addSearchQuery(it).blockingGet()
        }

        val pastSearches = searchRepository.getPastSearches().blockingGet()
        expectThat(pastSearches).first().isEqualTo(queries.last())
    }

    @Test
    fun getPastSearches_isFirstAppRun_returnEmptyList() {
        val pastSearches = preferences.read(SearchRepository.PREF_PAST_SEARCHES, emptyList<String>()).blockingGet()
        expectThat(pastSearches).isEmpty()
    }

    @Test
    fun getPastSearches_showPastSearches_returnNotEmptyList() {
        repeat(3) {
            val query = "test $it"
            searchRepository.addSearchQuery(query).blockingGet()
        }

        val pastSearches = searchRepository.getPastSearches().blockingGet()
        expectThat(pastSearches).isNotEmpty()
    }

    @Test
    fun getPastSearches_showPastSearches_returnListWithCorrectSize() {
        // Add more queries than allowed
        repeat(SearchRepository.MAX_SEARCH_QUERIES + 1) {
            val query = "test $it"
            searchRepository.addSearchQuery(query).blockingGet()
        }

        val pastSearches = searchRepository.getPastSearches().blockingGet()
        expectThat(pastSearches).hasSize(SearchRepository.MAX_SEARCH_QUERIES)
    }
}
