package cc.foxa.flip.tagedit

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import cc.foxa.flip.di.FragmentScope
import cc.foxa.flip.di.ViewModelKey

@Module
abstract class TagEditModule {
    @FragmentScope
    @ContributesAndroidInjector
    abstract fun tagEditFragment(): TagEditFragment

    @Binds
    @IntoMap
    @ViewModelKey(TagEditViewModel::class)
    abstract fun bindViewModel(viewmodel: TagEditViewModel): ViewModel
}