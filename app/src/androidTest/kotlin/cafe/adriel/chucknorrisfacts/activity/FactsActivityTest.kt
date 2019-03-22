package cafe.adriel.chucknorrisfacts.activity

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.activityScenarioRule
import cafe.adriel.chucknorrisfacts.R
import cafe.adriel.chucknorrisfacts.matcher.childOf
import cafe.adriel.chucknorrisfacts.mock.MockFactsService
import cafe.adriel.chucknorrisfacts.presentation.facts.FactsActivity
import com.schibsted.spain.barista.assertion.BaristaListAssertions.assertListNotEmpty
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.schibsted.spain.barista.interaction.BaristaClickInteractions.clickOn
import com.schibsted.spain.barista.interaction.BaristaEditTextInteractions.writeTo
import com.schibsted.spain.barista.interaction.BaristaKeyboardInteractions.pressImeActionButton
import com.schibsted.spain.barista.interaction.BaristaMenuClickInteractions.clickMenu
import org.junit.Test

class FactsActivityTest : BaseActivityTest<FactsActivity>() {

    override val activityRule = activityScenarioRule<FactsActivity>()

    @Test
    fun searchWithUserInput_ShowItems() {
        searchByQuery(MockFactsService.QUERY_SUCCESS_RESULT)
        assertListNotEmpty(R.id.vFacts)
    }

    @Test
    fun searchWithSuggestion_ShowItems() {
        searchBySuggestion(0)
        assertListNotEmpty(R.id.vFacts)
    }

    @Test
    fun searchWithPastSearch_ShowItems() {
        searchByQuery(MockFactsService.QUERY_SUCCESS_RESULT)
        searchByPastSearch(MockFactsService.QUERY_SUCCESS_RESULT)
        assertListNotEmpty(R.id.vFacts)
    }

    @Test
    fun searchWithQueryWithNoItem_ShowEmptyState() {
        searchByQuery(MockFactsService.QUERY_EMPTY_RESULT)
        assertDisplayed(R.id.vStateTitle, R.string.no_facts)
    }

    @Test
    fun searchWithNotSupportedQuery_ShowErrorState() {
        searchByQuery(MockFactsService.QUERY_ERROR_RESULT)
        assertDisplayed(R.id.vStateTitle, R.string.something_went_wrong)
    }

    private fun searchByQuery(query: String){
        clickMenu(R.id.action_search)
        writeTo(R.id.vQuery, query)
        pressImeActionButton()
    }

    private fun searchBySuggestion(position: Int){
        clickMenu(R.id.action_search)
        onView(childOf(withId(R.id.vSuggestions), position)).perform(click())
    }

    private fun searchByPastSearch(query: String){
        clickMenu(R.id.action_search)
        clickOn(query)
    }

}
