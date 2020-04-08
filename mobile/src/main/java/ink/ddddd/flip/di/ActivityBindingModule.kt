package ink.ddddd.flip.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ink.ddddd.flip.perform.PerformActivity
import ink.ddddd.flip.perform.PerformModule

@Module
abstract class ActivityBindingModule {
    @ActivityScope
    @ContributesAndroidInjector(modules = [PerformModule::class])
    abstract fun performActivity(): PerformActivity
}