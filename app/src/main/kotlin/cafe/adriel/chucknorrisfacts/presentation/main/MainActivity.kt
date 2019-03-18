package cafe.adriel.chucknorrisfacts.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import cafe.adriel.chucknorrisfacts.R
import cafe.adriel.chucknorrisfacts.model.Fact
import com.google.android.material.card.MaterialCardView
import com.link184.kidadapter.setUp
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_fact.view.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = vFacts.setUp<Fact> {
            withLayoutManager(LinearLayoutManager(this@MainActivity))
            withLayoutResId(R.layout.item_fact)
            bind { item ->
                vFact.text = item.text
                vCategory.text = item.categories?.firstOrNull() ?: getString(R.string.uncategorized)

                if(this is MaterialCardView){
                    isClickable = false
                }
                vShare.setOnClickListener { share(item) }
            }

        }
    }

    private fun share(fact: Fact){
        // TODO
    }

}
