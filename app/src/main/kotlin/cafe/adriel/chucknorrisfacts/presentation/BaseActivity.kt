package cafe.adriel.chucknorrisfacts.presentation

import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.etiennelenhart.eiffel.state.ViewState
import com.etiennelenhart.eiffel.viewmodel.StateViewModel
import com.google.android.material.snackbar.Snackbar

abstract class BaseActivity<S : ViewState> : AppCompatActivity() {

    protected abstract val viewModel: StateViewModel<S>

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        viewModel.observeState(this, ::onStateUpdated)
    }

    protected abstract fun onStateUpdated(state: S)

    protected fun showMessage(@StringRes textResId: Int) = showMessage(getString(textResId))

    protected fun showMessage(text: String) =
        Snackbar.make(findViewById(android.R.id.content), text, Snackbar.LENGTH_SHORT).show()

}