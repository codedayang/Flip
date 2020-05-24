package cc.foxa.flip.perform

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import cc.foxa.flip.di.ActivityScope
import cc.foxa.flip.di.FragmentScope
import cc.foxa.flip.di.ViewModelKey

@Module
abstract class PerformModule {
    @FragmentScope
    @ContributesAndroidInjector
    abstract fun performFragment(): PerformFragment

    @Binds
    @IntoMap
    @ViewModelKey(PerformViewModel::class)
    abstract fun bindViewModel(viewmodel: PerformViewModel): ViewModel

}