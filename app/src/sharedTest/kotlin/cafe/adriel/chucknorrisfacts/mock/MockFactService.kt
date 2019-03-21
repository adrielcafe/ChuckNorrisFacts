package cafe.adriel.chucknorrisfacts.mock

import okhttp3.mock.Behavior.UNORDERED
import okhttp3.mock.ClasspathResources.resource
import okhttp3.mock.HttpCode.HTTP_500_INTERNAL_SERVER_ERROR
import okhttp3.mock.MediaTypes.MEDIATYPE_JSON
import okhttp3.mock.MockInterceptor
import okhttp3.mock.endsWith
import okhttp3.mock.get
import okhttp3.mock.rule
import okhttp3.mock.url

object MockFactService {

    const val QUERY_SUCCESS = "success"
    const val QUERY_EMPTY = "empty"
    const val QUERY_ERROR = "error"

    val mockInterceptor by lazy {
        MockInterceptor(UNORDERED).apply {
            // /search
            rule(get, url endsWith "/search?query=$QUERY_SUCCESS") {
                respond(resource("facts.json"), MEDIATYPE_JSON)
            }
            rule(get, url endsWith "/search?query=$QUERY_EMPTY") {
                respond(resource("facts_empty.json"), MEDIATYPE_JSON)
            }
            rule(get, url endsWith "/search?query=$QUERY_ERROR") {
                respond(HTTP_500_INTERNAL_SERVER_ERROR)
            }

            // /categories
            rule(get, url endsWith "/categories") {
                respond(resource("categories.json"), MEDIATYPE_JSON)
            }
        }
    }

}