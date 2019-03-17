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
        preferences.contains(PREF_FACT_CATEGORIES)
            .flatMap { hasCategories ->
                if(hasCategories){
                    getLocalCategories()
                } else {
                    getRemoteCategories()
                }
            }
            .map { categories ->
                // Return a Set with the first 8 categories
                categories.take(MAX_CATEGORIES).toSet()
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    private fun getLocalCategories() =
        preferences.read<Set<String>>(PREF_FACT_CATEGORIES, emptySet())

    private fun getRemoteCategories() =
        factService.getCategories()
            .doOnSuccess {
                preferences.write(PREF_FACT_CATEGORIES, it).blockingAwait()
            }

}