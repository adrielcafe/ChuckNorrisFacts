package cafe.adriel.chucknorrisfacts.presentation.facts

import android.app.Activity
import android.content.ClipDescription
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.core.app.ShareCompat
import cafe.adriel.chucknorrisfacts.R
import cafe.adriel.chucknorrisfacts.extension.intentFor
import cafe.adriel.chucknorrisfacts.extension.runIfConnected
import cafe.adriel.chucknorrisfacts.model.Fact
import cafe.adriel.chucknorrisfacts.presentation.BaseActivity
import cafe.adriel.chucknorrisfacts.presentation.BaseViewEvent
import cafe.adriel.chucknorrisfacts.presentation.search.SearchActivity
import com.bumptech.glide.Glide
import com.etiennelenhart.eiffel.state.peek
import com.google.android.material.card.MaterialCardView
import com.link184.kidadapter.setUp
import com.link184.kidadapter.simple.SingleKidAdapter
import kotlinx.android.synthetic.main.activity_facts.*
import kotlinx.android.synthetic.main.item_fact.view.*
import org.koin.android.viewmodel.ext.android.viewModel

class FactsActivity : BaseActivity<FactsViewState>() {

    companion object {
        const val REQUEST_QUERY = 0

        private const val LAYOUT_STATE_CONTENT = "content"
        private const val LAYOUT_STATE_LOADING = "loading"
        private const val LAYOUT_STATE_EMPTY = "empty"
        private const val LAYOUT_STATE_ERROR = "error"
    }

    override val viewModel by viewModel<FactsViewModel>()

    private lateinit var adapter: SingleKidAdapter<Fact>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_facts)
        setSupportActionBar(vToolbar)

        initLayoutState()
        initAdapter()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_QUERY) {
            if (resultCode == Activity.RESULT_OK) {
                val query = data?.getStringExtra(SearchActivity.RESULT_QUERY)
                query?.let { onQueryChanged(it) }
            }
            return
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.facts, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?) = when (item?.itemId) {
        R.id.action_search -> {
            openSearch()
            true
        }
        else -> false
    }

    override fun onStateUpdated(state: FactsViewState) {
        state.apply {
            if (facts.isEmpty()) {
                setLayoutState(LAYOUT_STATE_EMPTY)
            } else {
                setAdapterItems(facts)
                setLayoutState(LAYOUT_STATE_CONTENT)
            }

            event?.peek {
                when (it) {
                    is BaseViewEvent.Loading -> setLayoutState(LAYOUT_STATE_LOADING)
                    is BaseViewEvent.Error -> setLayoutState(LAYOUT_STATE_ERROR, it.message)
                }
                true
            }
        }
    }

    private fun onQueryChanged(query: String) {
        vToolbar.subtitle = "\"$query\""
        viewModel.setQuery(query)
    }

    private fun initLayoutState() {
        val layoutInflater = LayoutInflater.from(this)
        with(vStateLayout) {
            setStateView(LAYOUT_STATE_LOADING, layoutInflater.inflate(R.layout.state_loading, null))
            setStateView(LAYOUT_STATE_ERROR, layoutInflater.inflate(R.layout.state_error, null))
            setStateView(LAYOUT_STATE_EMPTY, layoutInflater.inflate(R.layout.state_empty, null).apply {
                // Load empty state image
                Glide.with(this)
                    .asGif()
                    .load(R.drawable.state_empty)
                    .into(findViewById(R.id.vStateImage))
            })
        }
    }

    private fun initAdapter() {
        vFacts.itemAnimator = null
        adapter = vFacts.setUp<Fact> {
            withLayoutResId(R.layout.item_fact)
            bind { fact ->
                if (this is MaterialCardView) isClickable = false

                vFact.text = fact.text
                vFact.textSize = viewModel.getFactTextSize(fact)

                vCategory.text = viewModel.getFactCategory(fact)
                vCategory.isAllCaps = true

                vShare.setOnClickListener { shareFact(fact) }
            }
        }
    }

    private fun setLayoutState(state: String, message: String? = null) {
        vStateLayout.state = state
        message?.let {
            vStateLayout.getStateView(state)
                ?.findViewById<TextView>(R.id.vStateMessage)
                ?.text = it
        }
    }

    private fun setAdapterItems(facts: List<Fact>) {
        adapter.clear()
        adapter + facts
    }

    private fun shareFact(fact: Fact) {
        val text = viewModel.getFactShareText(fact)
        ShareCompat.IntentBuilder
            .from(this)
            .setText(text)
            .setType(ClipDescription.MIMETYPE_TEXT_PLAIN)
            .startChooser()
    }

    private fun openSearch() {
        runIfConnected(true) {
            startActivityForResult(intentFor<SearchActivity>(), REQUEST_QUERY)
        }
    }
}
