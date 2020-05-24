package cc.foxa.flip.di

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import cc.foxa.flip.di.ViewModelFactory

@Module
abstract class ViewModelFactoryModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}