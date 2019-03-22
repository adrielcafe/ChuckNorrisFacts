package cafe.adriel.chucknorrisfacts.viewmodel

import cafe.adriel.chucknorrisfacts.BaseTest
import cafe.adriel.chucknorrisfacts.presentation.search.SearchViewModel
import org.junit.Test
import org.koin.test.inject
import strikt.api.expectThat
import strikt.assertions.endsWith
import strikt.assertions.isLowerCase
import strikt.assertions.isNotEqualTo
import strikt.assertions.startsWith

class SearchViewModelTest : BaseTest() {
    private val viewModel by inject<SearchViewModel>()

    @Test
    fun formatQuery_BeforeShowQuery_ReturnFormattedQuery() {
        val unformattedQuery = " Query "

        val formattedQuery = viewModel.formatQuery(unformattedQuery)
        expectThat(formattedQuery) {
            isNotEqualTo(unformattedQuery)
            isLowerCase()
            not { startsWith(' ') }
            not { endsWith(' ') }
        }
    }
}
