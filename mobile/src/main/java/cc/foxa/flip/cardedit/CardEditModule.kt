package cc.foxa.flip.cardedit

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import cc.foxa.flip.di.FragmentScope
import cc.foxa.flip.di.ViewModelKey
import cc.foxa.flip.tagedit.TagEditModule

@Module
abstract class CardEditModule {
    @FragmentScope
    @ContributesAndroidInjector
    abstract fun cardEditFragment(): CardEditFragment

    @Binds
    @ViewModelKey(CardEditViewModel::class)
    @IntoMap
    abstract fun bindViewModel(viewModel: CardEditViewModel): ViewModel


}