package ink.ddddd.flip.onboarding

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ink.ddddd.flip.di.ViewModelKey

@Module
abstract class OnBoardingModule {
    @Binds
    @IntoMap
    @ViewModelKey(OnBoardingViewModel::class)
    abstract fun viewModel(onBoardingViewModel: OnBoardingViewModel): ViewModel
}