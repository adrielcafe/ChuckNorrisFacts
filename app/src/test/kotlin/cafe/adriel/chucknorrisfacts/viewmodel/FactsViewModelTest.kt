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
    fun getFactCategory_FactHasCategory_ReturnFirstCategory() {
        val factCategories = listOf("category 1", "category 2")
        val fact = Fact("test", "http://chuck.norris", factCategories)

        val category = viewModel.getFactCategory(fact)
        expectThat(category).isEqualTo(factCategories[0])
    }

    @Test
    fun getFactCategory_FactHasNoCategory_ReturnUncategorized() {
        val fact = Fact("test", "http://chuck.norris")
        val uncategorizedString = appContext.getString(R.string.uncategorized)

        val category = viewModel.getFactCategory(fact)
        expectThat(category).isEqualTo(uncategorizedString)
    }

    @Test
    fun getFactTextSize_FactHasShortText_ReturnBigTextSize() {
        val shortText = "".padEnd(FactsViewModel.FACT_TEXT_LENGTH_LIMIT, 'a')
        val fact = Fact(shortText, "http://chuck.norris")

        val textSize = viewModel.getFactTextSize(fact)
        expectThat(textSize).isEqualTo(FactsViewModel.FACT_TEXT_SIZE_BIG)
    }

    @Test
    fun getFactTextSize_FactHasLongText_ReturnSmallTextSize() {
        val longText = "".padEnd(FactsViewModel.FACT_TEXT_LENGTH_LIMIT + 1, 'a')
        val fact = Fact(longText, "http://chuck.norris")

        val textSize = viewModel.getFactTextSize(fact)
        expectThat(textSize).isEqualTo(FactsViewModel.FACT_TEXT_SIZE_SMALL)
    }

    @Test
    fun getFactShareText_ShareFact_TextContainsUrl() {
        val fact = Fact("test", "http://chuck.norris")

        val shareText = viewModel.getFactShareText(fact)
        expectThat(shareText) {
            isNotEmpty()
            containsIgnoringCase(fact.url)
        }
    }
}
