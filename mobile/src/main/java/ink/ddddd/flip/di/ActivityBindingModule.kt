package ink.ddddd.flip.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ink.ddddd.flip.MainActivity
import ink.ddddd.flip.cardbrowse.CardBrowseModule
import ink.ddddd.flip.cardedit.CardEditModule
import ink.ddddd.flip.perform.PerformModule
import ink.ddddd.flip.tagedit.TagEditModule
import ink.ddddd.flip.temp.TempModule

@Module
abstract class ActivityBindingModule {
    @ActivityScope
    @ContributesAndroidInjector(
        modules = [
            PerformModule::class,
            CardEditModule::class,
            CardBrowseModule::class,
            TagEditModule::class,
            TempModule::class]
    )
    abstract fun mainActivity(): MainActivity
}