package ink.ddddd.flip.perform

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import ink.ddddd.flip.di.ActivityScope
import ink.ddddd.flip.di.FragmentScope
import ink.ddddd.flip.di.ViewModelKey

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