package cafe.adriel.chucknorrisfacts.presentation

import androidx.lifecycle.MutableLiveData
import com.etiennelenhart.eiffel.state.ViewState
import com.etiennelenhart.eiffel.viewmodel.StateViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel<S : ViewState> : StateViewModel<S>() {

    override val state = MutableLiveData<S>()
    protected val disposables = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}
