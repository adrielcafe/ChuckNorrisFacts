package cafe.adriel.chucknorrisfacts.repository.facts

import cafe.adriel.chucknorrisfacts.model.response.FactResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface FactsService {

    @GET("search")
    fun getFacts(@Query("query") query: String): Single<FactResponse>

    @GET("categories")
    fun getCategories(): Single<Set<String>>
}
