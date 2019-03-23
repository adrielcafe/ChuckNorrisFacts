package cafe.adriel.chucknorrisfacts.repository

import cafe.adriel.chucknorrisfacts.BaseTest
import cafe.adriel.chucknorrisfacts.mock.MockFactsService
import cafe.adriel.chucknorrisfacts.repository.facts.FactsRepository
import com.pacoworks.rxpaper2.RxPaperBook
import org.junit.Test
import org.koin.test.inject
import retrofit2.HttpException
import strikt.api.expectThat
import strikt.api.expectThrows
import strikt.assertions.hasSize
import strikt.assertions.isEmpty
import strikt.assertions.isNotEmpty
import strikt.assertions.isNotEqualTo

class FactsRepositoryTest : BaseTest() {
    private val factsRepository by inject<FactsRepository>()
    private val preferences by inject<RxPaperBook>()

    @Test
    fun getFacts_queryHasItem_returnNotEmptyList() {
        val facts = factsRepository.getFacts(MockFactsService.QUERY_SUCCESS_RESULT).blockingGet()
        expectThat(facts).isNotEmpty()
    }

    @Test
    fun getFacts_queryHasNoItem_returnEmptyList() {
        val facts = factsRepository.getFacts(MockFactsService.QUERY_EMPTY_RESULT).blockingGet()
        expectThat(facts).isEmpty()
    }

    @Test
    fun getFacts_queryIsNotSupported_throwException() {
        expectThrows<HttpException> {
            factsRepository.getFacts(MockFactsService.QUERY_ERROR_RESULT).blockingGet()
        }
    }

    @Test
    fun getCategories_iFirstAppRun_returnEmptySet() {
        val categories = preferences.read(FactsRepository.PREF_FACT_CATEGORIES, emptySet<String>()).blockingGet()
        expectThat(categories).isEmpty()
    }

    @Test
    fun getCategories_showCategories_returnNotEmptySet() {
        val categories = factsRepository.getCategories().blockingGet()
        expectThat(categories).isNotEmpty()
    }

    @Test
    fun getCategories_showCategories_returnSetWithCorrectSize() {
        val categories = factsRepository.getCategories().blockingGet()
        expectThat(categories).hasSize(FactsRepository.MAX_CATEGORIES)
    }

    @Test
    fun getCategories_showCategoriesTwice_categoriesAreDifferent() {
        val categoriesFirstTime = factsRepository.getCategories().blockingGet()
        val categoriesSecondTime = factsRepository.getCategories().blockingGet()
        expectThat(categoriesFirstTime).isNotEqualTo(categoriesSecondTime)
    }
}
