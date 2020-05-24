package cc.foxa.flip.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import cc.foxa.flip.FlipApplication
import cc.foxa.flip.shared.di.SharedModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ActivityBindingModule::class,
        SharedModule::class,
        ViewModelFactoryModule::class]
)
interface AppComponent : AndroidInjector<FlipApplication> {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): AppComponent
    }

}