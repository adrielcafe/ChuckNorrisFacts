package cafe.adriel.chucknorrisfacts.repository.fact

import com.pacoworks.rxpaper2.RxPaperBook
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class FactRepository(private val factService: FactService, private val preferences: RxPaperBook) {

    companion object {
        private const val PREF_FACT_CATEGORIES = "factCategories"
        private const val MAX_CATEGORIES = 8
    }

    fun getFacts(query: String) =
        factService.getFacts(query)
            .map { response ->
                response.result
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun getCategories() =
        getLocalCategories()
            .map { categories ->
                if(categories.isEmpty()){
                    getRemoteCategories().blockingGet()
                } else {
                    categories
                }
            }
            .map { categories ->
                // Select 8 random categories
                categories.shuffled().take(MAX_CATEGORIES).toSet()
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    private fun getLocalCategories() =
        preferences.read<Set<String>>(PREF_FACT_CATEGORIES, emptySet())

    private fun getRemoteCategories() =
        factService.getCategories()
            .doOnSuccess {
                // Cache the result
                preferences.write(PREF_FACT_CATEGORIES, it).blockingAwait()
            }

}