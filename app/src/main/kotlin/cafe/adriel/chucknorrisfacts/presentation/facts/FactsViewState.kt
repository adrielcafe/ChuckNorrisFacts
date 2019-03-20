package cafe.adriel.chucknorrisfacts.presentation.facts

import cafe.adriel.chucknorrisfacts.model.Fact
import cafe.adriel.chucknorrisfacts.presentation.BaseViewEvent
import com.etiennelenhart.eiffel.state.ViewState

data class FactsViewState(
    val facts: List<Fact> = emptyList(),
    val event: BaseViewEvent? = null
) : ViewState
