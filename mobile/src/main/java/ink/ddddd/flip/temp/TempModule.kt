package ink.ddddd.flip.temp

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import ink.ddddd.flip.di.FragmentScope
import ink.ddddd.flip.di.ViewModelKey

@Module
abstract class TempModule {
    @FragmentScope
    @ContributesAndroidInjector
    abstract fun tempFragment(): TempFragment

    @Binds
    @IntoMap
    @ViewModelKey(TempViewModel::class)
    abstract fun bindViewModel(tempViewModel: TempViewModel): ViewModel
}