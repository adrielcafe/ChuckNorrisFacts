package cafe.adriel.chucknorrisfacts.presentation.search

import com.etiennelenhart.eiffel.state.ViewState

data class SearchState(
    val suggestions: Set<String> = emptySet(),
    val pastSearches: List<String> = emptyList()
) : ViewState