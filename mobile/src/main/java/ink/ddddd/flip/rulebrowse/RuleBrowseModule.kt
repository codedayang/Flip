package ink.ddddd.flip.rulebrowse

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import ink.ddddd.flip.di.FragmentScope
import ink.ddddd.flip.di.ViewModelKey

@Module
abstract class RuleBrowseModule {
    @FragmentScope
    @ContributesAndroidInjector
    abstract fun ruleBrowseFragment(): RuleBrowseFragment

    @Binds
    @IntoMap
    @ViewModelKey(RuleBrowseViewModel::class)
    abstract fun ruleBrowseViewModel(viewModel: RuleBrowseViewModel): ViewModel
}