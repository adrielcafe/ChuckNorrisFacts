package cafe.adriel.chucknorrisfacts.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.etiennelenhart.eiffel.state.ViewState
import com.etiennelenhart.eiffel.viewmodel.StateViewModel

abstract class BaseActivity<S : ViewState> : AppCompatActivity() {

    protected abstract val viewModel: StateViewModel<S>

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        viewModel.observeState(this, ::onStateUpdated)
    }

    protected abstract fun onStateUpdated(state: S)

}