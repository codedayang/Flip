package cc.foxa.flip.tagbrowse

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import cc.foxa.flip.di.FragmentScope
import cc.foxa.flip.di.ViewModelKey

@Module
abstract class TagBrowseModule {
    @FragmentScope
    @ContributesAndroidInjector
    abstract fun fragment(): TagBrowseFragment

    @Binds
    @IntoMap
    @ViewModelKey(TagBrowseViewModel::class)
    abstract fun viewModel(viewModel: TagBrowseViewModel): ViewModel
}