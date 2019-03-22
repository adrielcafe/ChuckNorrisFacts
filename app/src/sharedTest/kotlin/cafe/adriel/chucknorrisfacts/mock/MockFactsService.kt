package cafe.adriel.chucknorrisfacts.mock

import okhttp3.mock.Behavior.UNORDERED
import okhttp3.mock.ClasspathResources.resource
import okhttp3.mock.HttpCode.HTTP_500_INTERNAL_SERVER_ERROR
import okhttp3.mock.MediaTypes.MEDIATYPE_JSON
import okhttp3.mock.MockInterceptor
import okhttp3.mock.endsWith
import okhttp3.mock.get
import okhttp3.mock.matches
import okhttp3.mock.rule
import okhttp3.mock.url

object MockFactsService {
    const val QUERY_SUCCESS_RESULT = "success"
    const val QUERY_EMPTY_RESULT = "empty"
    const val QUERY_ERROR_RESULT = "error"

    val mockInterceptor by lazy {
        MockInterceptor(UNORDERED).apply {
            // /search endpoint
            rule(get, url endsWith "/search?query=$QUERY_ERROR_RESULT", times = Int.MAX_VALUE) {
                respond(HTTP_500_INTERNAL_SERVER_ERROR)
            }
            rule(get, url endsWith "/search?query=$QUERY_EMPTY_RESULT", times = Int.MAX_VALUE) {
                respond(resource("facts_empty.json"), MEDIATYPE_JSON)
            }
            rule(get, url matches "^.*/search\\?query=\\w+$".toRegex(), times = Int.MAX_VALUE) {
                respond(resource("facts_success.json"), MEDIATYPE_JSON)
            }

            // /categories endpoint
            rule(get, url endsWith "/categories", times = Int.MAX_VALUE) {
                respond(resource("categories.json"), MEDIATYPE_JSON)
            }
        }
    }

}