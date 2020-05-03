package ink.ddddd.flip.cardedit

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import ink.ddddd.flip.di.FragmentScope
import ink.ddddd.flip.di.ViewModelKey
import ink.ddddd.flip.tagedit.TagEditModule

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