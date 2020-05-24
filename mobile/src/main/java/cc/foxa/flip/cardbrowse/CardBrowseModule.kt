package cc.foxa.flip.cardbrowse

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import cc.foxa.flip.di.FragmentScope
import cc.foxa.flip.di.ViewModelKey

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