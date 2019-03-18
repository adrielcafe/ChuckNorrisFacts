package cafe.adriel.chucknorrisfacts.presentation.fact

import android.content.ClipDescription
import android.os.Bundle
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
        const val FACT_TEXT_SIZE_BIG = 20f
        const val FACT_TEXT_SIZE_SMALL = 14f
    }

    private lateinit var adapter: SingleKidAdapter<Fact>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fact)

        initAdapter()
    }

    private fun initAdapter(){
        adapter = vFacts.setUp<Fact> {
            withLayoutResId(R.layout.item_fact)
            bind { item ->
                if(this is MaterialCardView) isClickable = false

                vFact.text = item.text
                vFact.textSize = getFactTextSize(item)
                vCategory.text = getFactCategory(item)

                vShare.setOnClickListener { shareFact(item) }
            }
        }
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
