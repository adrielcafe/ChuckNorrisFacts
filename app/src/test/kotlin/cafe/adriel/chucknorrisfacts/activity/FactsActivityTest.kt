package cafe.adriel.chucknorrisfacts.activity

import android.app.Activity
import android.app.Instrumentation.ActivityResult
import android.content.Intent
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.rule.IntentsTestRule
import cafe.adriel.chucknorrisfacts.BaseTest
import cafe.adriel.chucknorrisfacts.R
import cafe.adriel.chucknorrisfacts.mock.MockFactsService
import cafe.adriel.chucknorrisfacts.presentation.facts.FactsActivity
import cafe.adriel.chucknorrisfacts.presentation.search.SearchActivity
import com.schibsted.spain.barista.assertion.BaristaListAssertions.assertListNotEmpty
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.schibsted.spain.barista.interaction.BaristaListInteractions.clickListItemChild
import com.schibsted.spain.barista.interaction.BaristaMenuClickInteractions.clickMenu
import org.junit.Rule
import org.junit.Test

class FactsActivityTest : BaseTest() {

    @get:Rule
    val intentsTestRule = IntentsTestRule(FactsActivity::class.java, true, true)

    @Test
    fun searchWithUserInput_showItems() {
        search(MockFactsService.QUERY_SUCCESS_RESULT)

        assertListNotEmpty(R.id.vFacts)
    }

    @Test
    fun searchWithQueryWithNoItem_showEmptyState() {
        search(MockFactsService.QUERY_EMPTY_RESULT)

        assertDisplayed(R.id.vStateTitle, R.string.no_facts)
    }

    @Test
    fun searchWithEmptyQuery_showEmptyState() {
        search("")

        assertDisplayed(R.id.vStateTitle, R.string.no_facts)
    }

    @Test
    fun searchWithNotSupportedQuery_showErrorState() {
        search(MockFactsService.QUERY_ERROR_RESULT)

        assertDisplayed(R.id.vStateTitle, R.string.something_went_wrong)
    }

    @Test
    fun searchCancelled_doNothing() {
        intending(hasComponent(SearchActivity::class.java.name))
            .respondWith(ActivityResult(Activity.RESULT_CANCELED, null))

        assertDisplayed(R.id.vStateTitle, R.string.no_facts)
    }

    @Test
    fun shareFact_launchChooserIntent() {
        search(MockFactsService.QUERY_SUCCESS_RESULT)
        clickListItemChild(R.id.vFacts, 0, R.id.vShare)

        intended(hasAction(Intent.ACTION_CHOOSER))
    }

    private fun search(query: String) {
        val resultIntent = Intent().putExtra(SearchActivity.RESULT_QUERY, query)
        intending(hasComponent(SearchActivity::class.java.name))
            .respondWith(ActivityResult(Activity.RESULT_OK, resultIntent))

        clickMenu(R.id.action_search)
    }
}
