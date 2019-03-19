package cafe.adriel.chucknorrisfacts.presentation.facts

import cafe.adriel.chucknorrisfacts.model.Fact
import com.etiennelenhart.eiffel.state.ViewState

data class FactsState(
    val facts: List<Fact> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
) : ViewState