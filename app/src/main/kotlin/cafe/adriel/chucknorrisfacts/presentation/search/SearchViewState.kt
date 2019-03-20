package cafe.adriel.chucknorrisfacts.presentation.search

import cafe.adriel.chucknorrisfacts.presentation.BaseViewEvent
import com.etiennelenhart.eiffel.state.ViewState

data class SearchViewState(
    val suggestions: Set<String> = emptySet(),
    val pastSearches: List<String> = emptyList(),
    val event: BaseViewEvent? = null
) : ViewState
