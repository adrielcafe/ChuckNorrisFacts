package cafe.adriel.chucknorrisfacts.viewmodel

import cafe.adriel.chucknorrisfacts.BaseTest
import cafe.adriel.chucknorrisfacts.presentation.search.SearchViewModel
import org.junit.Test
import org.koin.test.inject
import strikt.api.expectThat
import strikt.assertions.endsWith
import strikt.assertions.isFalse
import strikt.assertions.isLowerCase
import strikt.assertions.isNotEqualTo
import strikt.assertions.isTrue
import strikt.assertions.startsWith

class SearchViewModelTest : BaseTest() {
    private val viewModel by inject<SearchViewModel>()

    @Test
    fun formatQuery_QueryIsSelected_ReturnFormattedQuery() {
        val unformattedQuery = " Query "

        val formattedQuery = viewModel.formatQuery(unformattedQuery)
        expectThat(formattedQuery) {
            isNotEqualTo(unformattedQuery)
            isLowerCase()
            not { startsWith(' ') }
            not { endsWith(' ') }
        }
    }

    @Test
    fun isQueryValid_QueryIsEmpty_ReturnFalse() {
        val query = ""

        val isValid = viewModel.isQueryValid(query)
        expectThat(isValid).isFalse()
    }

    @Test
    fun isQueryValid_QueryIsTooShort_ReturnFalse() {
        val query = "de"

        val isValid = viewModel.isQueryValid(query)
        expectThat(isValid).isFalse()
    }

    @Test
    fun isQueryValid_QueryIsLongEnough_ReturnTrue() {
        val query = "dev"

        val isValid = viewModel.isQueryValid(query)
        expectThat(isValid).isTrue()
    }

    @Test
    fun isQueryValid_QueryIsVeryLong_ReturnTrue() {
        val query = "Chuck Norris can unit test entire apps with a single assert"

        val isValid = viewModel.isQueryValid(query)
        expectThat(isValid).isTrue()
    }
}
