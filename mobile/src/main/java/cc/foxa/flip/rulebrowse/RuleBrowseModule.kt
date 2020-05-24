package cc.foxa.flip.rulebrowse

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import cc.foxa.flip.di.FragmentScope
import cc.foxa.flip.di.ViewModelKey

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