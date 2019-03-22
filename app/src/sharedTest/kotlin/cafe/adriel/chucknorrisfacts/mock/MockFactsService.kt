package cafe.adriel.chucknorrisfacts.mock

import cafe.adriel.chucknorrisfacts.BuildConfig
import okhttp3.mock.Behavior.UNORDERED
import okhttp3.mock.ClasspathResources.resource
import okhttp3.mock.HttpCode.HTTP_500_INTERNAL_SERVER_ERROR
import okhttp3.mock.MediaTypes.MEDIATYPE_JSON
import okhttp3.mock.MockInterceptor
import okhttp3.mock.eq
import okhttp3.mock.get
import okhttp3.mock.rule
import okhttp3.mock.startWith
import okhttp3.mock.url

object MockFactsService {

    private const val API_URL = BuildConfig.CHUCK_NORRIS_API_BASE_URL
    const val QUERY_SUCCESS_RESULT = "success"
    const val QUERY_EMPTY_RESULT = "empty"
    const val QUERY_ERROR_RESULT = "error"

    val mockInterceptor by lazy {
        MockInterceptor(UNORDERED).apply {
            // /search
            rule(get, url eq "${API_URL}search?query=$QUERY_ERROR_RESULT", times = Int.MAX_VALUE) {
                respond(HTTP_500_INTERNAL_SERVER_ERROR)
            }
            rule(get, url eq "${API_URL}search?query=$QUERY_EMPTY_RESULT", times = Int.MAX_VALUE) {
                respond(resource("facts_empty.json"), MEDIATYPE_JSON)
            }
            rule(get, url startWith "${API_URL}search?query=", times = Int.MAX_VALUE) {
                respond(resource("facts_success.json"), MEDIATYPE_JSON)
            }

            // /categories
            rule(get, url eq "${API_URL}categories", times = Int.MAX_VALUE) {
                respond(resource("categories.json"), MEDIATYPE_JSON)
            }
        }
    }

}