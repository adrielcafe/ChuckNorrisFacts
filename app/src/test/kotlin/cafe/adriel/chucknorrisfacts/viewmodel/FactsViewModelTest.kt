package cafe.adriel.chucknorrisfacts.viewmodel

import cafe.adriel.chucknorrisfacts.BaseTest
import cafe.adriel.chucknorrisfacts.R
import cafe.adriel.chucknorrisfacts.model.Fact
import cafe.adriel.chucknorrisfacts.presentation.facts.FactsViewModel
import org.junit.Test
import org.koin.test.inject
import strikt.api.expectThat
import strikt.assertions.containsIgnoringCase
import strikt.assertions.isEqualTo
import strikt.assertions.isNotEmpty

class FactsViewModelTest : BaseTest() {
    private val viewModel by inject<FactsViewModel>()

    @Test
    fun getFactCategory_factHasCategory_returnFirstCategory() {
        val factCategories = listOf("category 1", "category 2")
        val fact = Fact("test", "http://chuck.norris", factCategories)

        val category = viewModel.getFactCategory(fact)
        expectThat(category).isEqualTo(factCategories[0])
    }

    @Test
    fun getFactCategory_factHasNoCategory_returnUncategorized() {
        val fact = Fact("test", "http://chuck.norris")
        val uncategorizedString = appContext.getString(R.string.uncategorized)

        val category = viewModel.getFactCategory(fact)
        expectThat(category).isEqualTo(uncategorizedString)
    }

    @Test
    fun getFactTextSize_factHasShortText_returnBigTextSize() {
        val shortText = "".padEnd(FactsViewModel.FACT_TEXT_LENGTH_THRESHOLD, 'a')
        val fact = Fact(shortText, "http://chuck.norris")

        val textSize = viewModel.getFactTextSize(fact)
        expectThat(textSize).isEqualTo(FactsViewModel.FACT_TEXT_SIZE_BIG)
    }

    @Test
    fun getFactTextSize_factHasLongText_returnSmallTextSize() {
        val longText = "".padEnd(FactsViewModel.FACT_TEXT_LENGTH_THRESHOLD + 1, 'a')
        val fact = Fact(longText, "http://chuck.norris")

        val textSize = viewModel.getFactTextSize(fact)
        expectThat(textSize).isEqualTo(FactsViewModel.FACT_TEXT_SIZE_SMALL)
    }

    @Test
    fun getFactShareText_shareFact_textContainsUrl() {
        val fact = Fact("test", "http://chuck.norris")

        val shareText = viewModel.getFactShareText(fact)
        expectThat(shareText) {
            isNotEmpty()
            containsIgnoringCase(fact.url)
        }
    }
}
