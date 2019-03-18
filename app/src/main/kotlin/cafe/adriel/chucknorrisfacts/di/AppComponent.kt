package cafe.adriel.chucknorrisfacts.di

import android.content.Context
import cafe.adriel.chucknorrisfacts.BuildConfig
import cafe.adriel.chucknorrisfacts.presentation.facts.FactsViewModel
import cafe.adriel.chucknorrisfacts.repository.ServiceFactory
import cafe.adriel.chucknorrisfacts.repository.fact.FactRepository
import cafe.adriel.chucknorrisfacts.repository.fact.FactService
import cafe.adriel.chucknorrisfacts.repository.search.SearchRepository
import com.pacoworks.rxpaper2.RxPaperBook
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

class AppComponent(private val appContext: Context) : Component {

    private val preferenceModule = module {
        single { RxPaperBook.with() }
    }
    private val serviceModule = module {
        single { ServiceFactory.newInstance<FactService>(BuildConfig.CHUCK_NORRIS_API_BASE_URL) }
    }
    private val repositoryModule = module {
        single { FactRepository(factService = get(), preferences = get()) }
        single { SearchRepository(preferences = get()) }
    }
    private val viewModelModule = module {
        viewModel { FactsViewModel(appContext = appContext, factRepository = get()) }
    }

    override fun getModules() = listOf(
        preferenceModule,
        serviceModule,
        repositoryModule,
        viewModelModule
    )
}
