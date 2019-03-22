package cafe.adriel.chucknorrisfacts.di

import org.koin.core.module.Module

interface Component {

    fun getModules(): Set<Module>
}
