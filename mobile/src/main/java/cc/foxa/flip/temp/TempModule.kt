package cc.foxa.flip.temp

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import cc.foxa.flip.di.FragmentScope
import cc.foxa.flip.di.ViewModelKey

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