package cafe.adriel.chucknorrisfacts.presentation.search

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.core.app.NavUtils
import cafe.adriel.chucknorrisfacts.R
import cafe.adriel.chucknorrisfacts.presentation.BaseActivity
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.activity_search.*
import org.koin.android.viewmodel.ext.android.viewModel

class SearchActivity : BaseActivity<SearchState>() {

    companion object {
        const val RESULT_QUERY = "query"
    }

    override val viewModel by viewModel<SearchViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setSupportActionBar(vToolbar)
        supportActionBar?.apply {
            setDisplayShowTitleEnabled(false)
            setDisplayHomeAsUpEnabled(true)
        }

        vQuery.setOnEditorActionListener { _, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_SEARCH){
                val query = vQuery.text
                if(!query.isNullOrBlank()){
                    returnQuery(query.toString())
                }
                true
            } else {
                false
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?) = when (item?.itemId) {
        android.R.id.home -> {
            NavUtils.navigateUpFromSameTask(this)
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onStateUpdated(state: SearchState) {
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
        }
    }

    private fun returnQuery(query: String){
        viewModel.saveQuery(query)

        val intent = Intent().putExtra(RESULT_QUERY, query)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    private fun addSuggestions(suggestions: Set<String>){
        vSuggestions.removeAllViews()
        suggestions.forEach { label ->
            val chipView = createChipView(label)
            vSuggestions.addView(chipView)
        }
    }

    private fun addPastSearches(pastSearches: List<String>){
        vPastSearches.removeAllViews()
        pastSearches.forEach { label ->
            val chipView = createChipView(label)
            vPastSearches.addView(chipView)
        }
    }

    private fun createChipView(label: String) =
        Chip(this).apply {
            text = label.toLowerCase()
            setOnClickListener { returnQuery(label) }
        }

}
