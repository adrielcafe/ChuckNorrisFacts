package cafe.adriel.chucknorrisfacts

import androidx.test.ext.junit.rules.activityScenarioRule
import cafe.adriel.chucknorrisfacts.presentation.facts.FactsActivity
import com.schibsted.spain.barista.assertion.BaristaListAssertions.assertListHasItems
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.schibsted.spain.barista.interaction.BaristaEditTextInteractions.writeTo
import com.schibsted.spain.barista.interaction.BaristaKeyboardInteractions.pressImeActionButton
import com.schibsted.spain.barista.interaction.BaristaMenuClickInteractions.clickMenu
import org.junit.Test

class FactsActivityTest : BaseActivityTest<FactsActivity>() {

    override val activityRule = activityScenarioRule<FactsActivity>()

    @Test
    fun checkSuccessResult() {
        searchFact(FactServiceInterceptor.QUERY_SUCCESS)
        assertListHasItems(R.id.vFacts)
    }

    @Test
    fun checkEmptyResult() {
        searchFact(FactServiceInterceptor.QUERY_EMPTY)
        assertDisplayed(R.id.vStateTitle, R.string.no_facts)
    }

    @Test
    fun checkErrorResult() {
        searchFact(FactServiceInterceptor.QUERY_ERROR)
        assertDisplayed(R.id.vStateTitle, R.string.something_went_wrong)
    }

    private fun searchFact(query: String){
        clickMenu(R.id.action_search)
        writeTo(R.id.vQuery, query)
        pressImeActionButton()
    }

}
