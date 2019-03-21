package cafe.adriel.chucknorrisfacts.activity

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.activityScenarioRule
import cafe.adriel.chucknorrisfacts.R
import cafe.adriel.chucknorrisfacts.matcher.nthChildOf
import cafe.adriel.chucknorrisfacts.mock.MockFactService
import cafe.adriel.chucknorrisfacts.presentation.facts.FactsActivity
import com.schibsted.spain.barista.assertion.BaristaListAssertions.assertListHasItems
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.schibsted.spain.barista.interaction.BaristaClickInteractions.clickOn
import com.schibsted.spain.barista.interaction.BaristaEditTextInteractions.writeTo
import com.schibsted.spain.barista.interaction.BaristaKeyboardInteractions.pressImeActionButton
import com.schibsted.spain.barista.interaction.BaristaMenuClickInteractions.clickMenu
import org.junit.Test

class FactsActivityTest : BaseActivityTest<FactsActivity>() {

    override val activityRule = activityScenarioRule<FactsActivity>()

    @Test
    fun checkUserInputResult() {
        searchQuery(MockFactService.QUERY_SUCCESS)
        assertListHasItems(R.id.vFacts)
    }

    @Test
    fun checkSuggestionResult() {
        searchSuggestion(0)
        assertListHasItems(R.id.vFacts)
    }

    @Test
    fun checkPastSearchResult() {
        searchQuery(MockFactService.QUERY_SUCCESS)
        searchPastSearch(MockFactService.QUERY_SUCCESS)
        assertListHasItems(R.id.vFacts)
    }

    @Test
    fun checkEmptyResult() {
        searchQuery(MockFactService.QUERY_EMPTY)
        assertDisplayed(R.id.vStateTitle, R.string.no_facts)
    }

    @Test
    fun checkErrorResult() {
        searchQuery(MockFactService.QUERY_ERROR)
        assertDisplayed(R.id.vStateTitle, R.string.something_went_wrong)
    }

    private fun searchQuery(query: String){
        clickMenu(R.id.action_search)
        writeTo(R.id.vQuery, query)
        pressImeActionButton()
    }

    private fun searchSuggestion(position: Int){
        clickMenu(R.id.action_search)
        onView(nthChildOf(withId(R.id.vSuggestions), position)).perform(click())
    }

    private fun searchPastSearch(query: String){
        clickMenu(R.id.action_search)
        clickOn(query)
    }

}
