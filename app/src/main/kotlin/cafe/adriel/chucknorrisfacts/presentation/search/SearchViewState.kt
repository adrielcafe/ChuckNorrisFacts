package cafe.adriel.chucknorrisfacts.presentation.search

import com.etiennelenhart.eiffel.state.ViewState

data class SearchViewState(
    val suggestions: Set<String> = emptySet(),
    val pastSearches: List<String> = emptyList()
) : ViewState
