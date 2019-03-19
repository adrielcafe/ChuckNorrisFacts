package cafe.adriel.chucknorrisfacts.presentation

import com.etiennelenhart.eiffel.state.ViewEvent

sealed class BaseViewEvent : ViewEvent() {
    class Loading: BaseViewEvent()
    class Error(val message: String): BaseViewEvent()
}