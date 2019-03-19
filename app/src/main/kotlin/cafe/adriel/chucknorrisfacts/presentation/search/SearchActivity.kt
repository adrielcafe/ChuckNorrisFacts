package cafe.adriel.chucknorrisfacts.presentation.search

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.core.app.NavUtils
import cafe.adriel.chucknorrisfacts.R
import cafe.adriel.chucknorrisfacts.presentation.BaseActivity
import cafe.adriel.chucknorrisfacts.presentation.BaseViewEvent
import com.etiennelenhart.eiffel.state.peek
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.activity_search.*
import org.koin.android.viewmodel.ext.android.viewModel

class SearchActivity : BaseActivity<SearchViewState>() {

    companion object {
        const val RESULT_QUERY = "query"
    }

    override val viewModel by viewModel<SearchViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(cafe.adriel.chucknorrisfacts.R.layout.activity_search)
        setSupportActionBar(vToolbar)
        supportActionBar?.apply {
            setDisplayShowTitleEnabled(false)
            setDisplayHomeAsUpEnabled(true)
        }

        initQueryInput()
    }

    override fun onOptionsItemSelected(item: MenuItem?) = when (item?.itemId) {
        android.R.id.home -> {
            NavUtils.navigateUpFromSameTask(this)
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onStateUpdated(state: SearchViewState) {
        state.apply {
            if(suggestions.isEmpty()){
                vSuggestionsLabel.visibility = View.GONE
                vSuggestions.visibility = View.GONE
            } else {
                vSuggestionsLabel.visibility = View.VISIBLE
                vSuggestions.visibility = View.VISIBLE
                addSuggestions(suggestions)
            }

            if(pastSearches.isEmpty()){
                vPastSearchesLabel.visibility = View.GONE
                vPastSearches.visibility = View.GONE
            } else {
                vPastSearchesLabel.visibility = View.VISIBLE
                vPastSearches.visibility = View.VISIBLE
                addPastSearches(pastSearches)
            }

            event?.peek {
                when(it) {
                    is BaseViewEvent.Error -> {
                        showMessage(it.message)
                        true
                    }
                    else -> false
                }
            }
        }
    }

    private fun initQueryInput(){
        vQuery.setOnEditorActionListener { _, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_SEARCH){
                vQuery.text?.let {
                    returnQuery(it.toString())
                }
                true
            } else {
                false
            }
        }
    }

    private fun returnQuery(query: String){
        if(query.isBlank()){
            return
        }

        val formattedQuery = viewModel.formatQuery(query)
        val intent = Intent().putExtra(RESULT_QUERY, formattedQuery)

        viewModel.saveQuery(formattedQuery)

        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    private fun addSuggestions(suggestions: Set<String>){
        vSuggestions.removeAllViews()
        suggestions.forEach { query ->
            val chipView = createChipView(query)
            vSuggestions.addView(chipView)
        }
    }

    private fun addPastSearches(pastSearches: List<String>){
        vPastSearches.removeAllViews()
        pastSearches.forEach { query ->
            val chipView = createChipView(query)
            vPastSearches.addView(chipView)
        }
    }

    private fun createChipView(query: String) =
        Chip(this).apply {
            text = viewModel.formatQuery(query)
            setTextColor(Color.WHITE)
            setChipBackgroundColorResource(R.color.colorAccent)
            setOnClickListener { returnQuery(query) }
        }

}
