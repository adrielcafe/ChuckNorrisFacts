package cafe.adriel.chucknorrisfacts.activity

import android.app.Activity
import androidx.test.espresso.intent.rule.IntentsTestRule
import cafe.adriel.chucknorrisfacts.BaseTest
import cafe.adriel.chucknorrisfacts.R
import cafe.adriel.chucknorrisfacts.mock.MockFactsService
import cafe.adriel.chucknorrisfacts.presentation.search.SearchActivity
import com.schibsted.spain.barista.interaction.BaristaEditTextInteractions.writeTo
import com.schibsted.spain.barista.interaction.BaristaKeyboardInteractions.pressImeActionButton
import org.junit.Rule
import org.junit.Test
import org.robolectric.Shadows.shadowOf
import strikt.api.expect
import strikt.assertions.isEqualTo
import strikt.assertions.isNotNull
import strikt.assertions.isNull

class SearchActivityTest : BaseTest() {

    @get:Rule
    val intentsTestRule = IntentsTestRule(SearchActivity::class.java, true, true)

    @Test
    fun validSearch_returnCorrectIntent() {
        searchByQuery(MockFactsService.QUERY_SUCCESS_RESULT)

        val resultCode = shadowOf(intentsTestRule.activity).resultCode
        val resultIntent = shadowOf(intentsTestRule.activity).resultIntent
        expect {
            that(resultCode).isEqualTo(Activity.RESULT_OK)
            that(resultIntent).isNotNull().and {
                that(resultIntent.getStringExtra(SearchActivity.RESULT_QUERY)).isEqualTo(MockFactsService.QUERY_SUCCESS_RESULT)
            }
        }
    }

    @Test
    fun invalidSearch_DoNothing() {
        searchByQuery("")

        val resultCode = shadowOf(intentsTestRule.activity).resultCode
        val resultIntent = shadowOf(intentsTestRule.activity).resultIntent
        expect {
            that(resultCode).isEqualTo(Activity.RESULT_CANCELED)
            that(resultIntent).isNull()
        }
    }

    private fun searchByQuery(query: String) {
        writeTo(R.id.vQuery, query)
        pressImeActionButton(R.id.vQuery)
    }
}
