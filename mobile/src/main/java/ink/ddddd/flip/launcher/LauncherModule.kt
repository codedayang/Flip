package ink.ddddd.flip.launcher

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ink.ddddd.flip.di.ViewModelKey

@Module
abstract class LauncherModule {
    @Binds
    @IntoMap
    @ViewModelKey(LauncherViewModel::class)
    abstract fun viewModel(launcherViewModel: LauncherViewModel): ViewModel
}