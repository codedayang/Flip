package cc.foxa.flip.ruleedit

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import cc.foxa.flip.di.FragmentScope
import cc.foxa.flip.di.ViewModelKey

@Module
abstract class RuleEditModule {
    @FragmentScope
    @ContributesAndroidInjector
    abstract fun ruleEditFragment(): RuleEditFragment

    @Binds
    @IntoMap
    @ViewModelKey(RuleEditViewModel::class)
    abstract fun ruleEditViewModel(viewModel: RuleEditViewModel): ViewModel
}