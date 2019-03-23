package cafe.adriel.chucknorrisfacts.di

import android.content.Context
import cafe.adriel.chucknorrisfacts.BuildConfig
import cafe.adriel.chucknorrisfacts.presentation.facts.FactsViewModel
import cafe.adriel.chucknorrisfacts.presentation.search.SearchViewModel
import cafe.adriel.chucknorrisfacts.repository.ServiceFactory
import cafe.adriel.chucknorrisfacts.repository.facts.FactsRepository
import cafe.adriel.chucknorrisfacts.repository.facts.FactsService
import cafe.adriel.chucknorrisfacts.repository.search.SearchRepository
import com.pacoworks.rxpaper2.RxPaperBook
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

class AppComponent(private val appContext: Context) : Component {

    private val preferenceModule = module {
        single { RxPaperBook.with() }
    }
    private val serviceModule = module {
        single { ServiceFactory.newInstance<FactsService>(BuildConfig.CHUCK_NORRIS_API_BASE_URL) }
    }
    private val repositoryModule = module {
        single { FactsRepository(factsService = get(), preferences = get()) }
        single { SearchRepository(preferences = get()) }
    }
    private val viewModelModule = module {
        viewModel { FactsViewModel(appContext = appContext, factsRepository = get()) }
        viewModel { SearchViewModel(factsRepository = get(), searchRepository = get()) }
    }

    override fun getModules() = setOf(
        preferenceModule,
        serviceModule,
        repositoryModule,
        viewModelModule
    )
}
