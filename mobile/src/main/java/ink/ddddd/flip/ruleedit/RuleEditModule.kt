package ink.ddddd.flip.ruleedit

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import ink.ddddd.flip.di.FragmentScope
import ink.ddddd.flip.di.ViewModelKey

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