package cafe.adriel.chucknorrisfacts.presentation.facts

import android.app.Activity
import android.content.ClipDescription
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import androidx.core.app.ShareCompat
import cafe.adriel.chucknorrisfacts.R
import cafe.adriel.chucknorrisfacts.extension.intentFor
import cafe.adriel.chucknorrisfacts.model.Fact
import cafe.adriel.chucknorrisfacts.presentation.BaseActivity
import cafe.adriel.chucknorrisfacts.presentation.search.SearchActivity
import com.google.android.material.card.MaterialCardView
import com.link184.kidadapter.setUp
import com.link184.kidadapter.simple.SingleKidAdapter
import kotlinx.android.synthetic.main.activity_facts.*
import kotlinx.android.synthetic.main.item_fact.view.*
import org.koin.android.viewmodel.ext.android.viewModel

class FactsActivity : BaseActivity<FactsState>() {

    companion object {
        private const val REQUEST_QUERY = 0

        private const val LAYOUT_STATE_CONTENT = "content"
        private const val LAYOUT_STATE_PROGRESS = "progress"
        private const val LAYOUT_STATE_EMPTY = "empty"
    }

    override val viewModel by viewModel<FactsViewModel>()

    private lateinit var adapter: SingleKidAdapter<Fact>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_facts)
        setSupportActionBar(vToolbar)

        vFacts.itemAnimator = null

        initLayoutState()
        initAdapter()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == REQUEST_QUERY){
            if(resultCode == Activity.RESULT_OK) {
                val query = data?.getStringExtra(SearchActivity.RESULT_QUERY)
                query?.let {
                    viewModel.setQuery(it)
                }
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
            startActivityForResult(intentFor<SearchActivity>(), REQUEST_QUERY)
            true
        }
        else -> false
    }

    override fun onStateUpdated(state: FactsState) {
        state.apply {
            when {
                isLoading -> setLayoutState(LAYOUT_STATE_PROGRESS)
                facts.isEmpty() -> setLayoutState(LAYOUT_STATE_EMPTY)
                else -> {
                    setAdapterItems(facts)
                    setLayoutState(LAYOUT_STATE_CONTENT)
                }
            }
        }
    }

    private fun initLayoutState(){
        val layoutInflater = LayoutInflater.from(this)
        vStateLayout.setStateView(LAYOUT_STATE_PROGRESS, layoutInflater.inflate(R.layout.state_loading, null))
        vStateLayout.setStateView(LAYOUT_STATE_EMPTY, layoutInflater.inflate(R.layout.state_empty, null))
        setLayoutState(LAYOUT_STATE_EMPTY)
    }

    private fun initAdapter(){
        adapter = vFacts.setUp<Fact> {
            withLayoutResId(R.layout.item_fact)
            bind { fact ->
                if(this is MaterialCardView) isClickable = false

                vFact.text = fact.text
                vFact.textSize = viewModel.getFactTextSize(fact)

                vCategory.text = viewModel.getFactCategory(fact)
                vCategory.isAllCaps = true

                vShare.setOnClickListener { shareFact(fact) }
            }
        }
    }

    private fun setLayoutState(state: String){
        vStateLayout.state = state
    }

    private fun setAdapterItems(facts: List<Fact>){
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

}
