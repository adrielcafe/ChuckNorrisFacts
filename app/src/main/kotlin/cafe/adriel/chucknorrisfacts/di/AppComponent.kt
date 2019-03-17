package cafe.adriel.chucknorrisfacts.di

import cafe.adriel.chucknorrisfacts.BuildConfig
import cafe.adriel.chucknorrisfacts.repository.ServiceFactory
import cafe.adriel.chucknorrisfacts.repository.fact.FactRepository
import cafe.adriel.chucknorrisfacts.repository.fact.FactService
import cafe.adriel.chucknorrisfacts.repository.search.SearchRepository
import com.pacoworks.rxpaper2.RxPaperBook
import org.koin.dsl.module

object AppComponent : Component {

    private val preferenceModule by lazy {
        module {
            single { RxPaperBook.with() }
        }
    }
    private val serviceModule by lazy {
        module {
            single { ServiceFactory.newInstance<FactService>(BuildConfig.CHUCK_NORRIS_API_BASE_URL) }
        }
    }
    private val repositoryModule by lazy {
        module {
            single { FactRepository(factService = get(), preferences = get()) }
            single { SearchRepository(preferences = get()) }
        }
    }
    private val viewModelModule by lazy {
        module {
            // TODO Add ViewModels when available
        }
    }

    override fun getModules() = listOf(
        preferenceModule,
        serviceModule,
        repositoryModule,
        viewModelModule
    )
}
