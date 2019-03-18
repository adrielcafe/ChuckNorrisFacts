package cafe.adriel.chucknorrisfacts.presentation.fact

import android.content.ClipDescription
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import cafe.adriel.chucknorrisfacts.R
import cafe.adriel.chucknorrisfacts.model.Fact
import com.google.android.material.card.MaterialCardView
import com.link184.kidadapter.setUp
import com.link184.kidadapter.simple.SingleKidAdapter
import kotlinx.android.synthetic.main.activity_fact.*
import kotlinx.android.synthetic.main.item_fact.view.*

class FactActivity : AppCompatActivity() {

    companion object {
        const val FACT_TEXT_LENGTH_LIMIT = 80
        const val FACT_TEXT_SIZE_BIG = 18f
        const val FACT_TEXT_SIZE_SMALL = 14f
    }

    private lateinit var adapter: SingleKidAdapter<Fact>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fact)
        setSupportActionBar(vToolbar)

        initAdapter()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.fact, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?) = when (item?.itemId) {
        R.id.action_search -> {
            // TODO
            true
        }
        else -> false
    }

    private fun initAdapter(){
        adapter = vFacts.setUp<Fact> {
            withLayoutResId(R.layout.item_fact)
            bind { fact ->
                if(this is MaterialCardView) isClickable = false

                vFact.text = fact.text
                vFact.textSize = getFactTextSize(fact)

                vCategory.text = getFactCategory(fact)
                vCategory.isAllCaps = true

                vShare.setOnClickListener { shareFact(fact) }
            }
        }

        // TODO test facts
        adapter + listOf(
            Fact("Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod", "..."),
            Fact("Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.", "..."),
            Fact("Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod", "..."),
            Fact("Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.", "...")
        )
    }

    private fun shareFact(fact: Fact) =
        ShareCompat.IntentBuilder
            .from(this)
            .setText(fact.url)
            .setType(ClipDescription.MIMETYPE_TEXT_PLAIN)
            .startChooser()

    private fun getFactCategory(fact: Fact) =
        fact.categories?.firstOrNull() ?: getString(R.string.uncategorized)

    private fun getFactTextSize(fact: Fact) =
        if(fact.text.length <= FACT_TEXT_LENGTH_LIMIT) FACT_TEXT_SIZE_BIG else FACT_TEXT_SIZE_SMALL

}
