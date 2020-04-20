package ink.ddddd.flip.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ink.ddddd.flip.MainActivity
import ink.ddddd.flip.perform.PerformFragment
import ink.ddddd.flip.perform.PerformModule

@Module
abstract class ActivityBindingModule {
    @ActivityScope
    @ContributesAndroidInjector(modules = [PerformModule::class])
    abstract fun mainActivity(): MainActivity
}