package cafe.adriel.chucknorrisfacts.presentation.facts

import android.content.Context
import androidx.lifecycle.MutableLiveData
import cafe.adriel.chucknorrisfacts.R
import cafe.adriel.chucknorrisfacts.model.Fact
import cafe.adriel.chucknorrisfacts.repository.fact.FactRepository
import com.etiennelenhart.eiffel.viewmodel.StateViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign

class FactsViewModel(val appContext: Context, val factRepository: FactRepository) : StateViewModel<FactsState>() {

    companion object {
        const val FACT_TEXT_LENGTH_LIMIT = 80
        const val FACT_TEXT_SIZE_BIG = 18f
        const val FACT_TEXT_SIZE_SMALL = 14f
    }

    override val state = MutableLiveData<FactsState>()
    private val disposables = CompositeDisposable()

    init {
        initState { FactsState() }
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }

    fun setQuery(query: String){
        updateState { it.copy(isLoading = true) }
        disposables += factRepository
            .getFacts(query)
            .subscribe({ facts ->
                updateState { it.copy(facts = facts, isLoading = false) }
            }, { error ->
                error.printStackTrace()
            })
    }

    fun getFactCategory(fact: Fact): String =
        fact.categories?.firstOrNull() ?: appContext.getString(R.string.uncategorized)

    fun getFactTextSize(fact: Fact): Float =
        if(fact.text.length <= FACT_TEXT_LENGTH_LIMIT) FACT_TEXT_SIZE_BIG else FACT_TEXT_SIZE_SMALL

    fun getFactShareText(fact: Fact): String =
        """Chuck Norris fact:
            ${fact.text}

            ${fact.url}
        """.trimIndent()

}