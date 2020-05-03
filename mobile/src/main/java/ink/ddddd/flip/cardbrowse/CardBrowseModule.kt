package ink.ddddd.flip.cardbrowse

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import ink.ddddd.flip.di.FragmentScope
import ink.ddddd.flip.di.ViewModelKey

@Module
abstract class CardBrowseModule {
    @FragmentScope
    @ContributesAndroidInjector
    abstract fun cardBrowseFragment(): CardBrowseFragment

    @Binds
    @IntoMap
    @ViewModelKey(CardBrowseViewModel::class)
    abstract fun cardBrowseViewModel(viewmodel: CardBrowseViewModel): ViewModel
}