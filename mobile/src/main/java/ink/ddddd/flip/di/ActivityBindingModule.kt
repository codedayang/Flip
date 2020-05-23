package ink.ddddd.flip.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ink.ddddd.flip.MainActivity
import ink.ddddd.flip.cardbrowse.CardBrowseModule
import ink.ddddd.flip.cardedit.CardEditModule
import ink.ddddd.flip.launcher.LauncherActivity
import ink.ddddd.flip.launcher.LauncherModule
import ink.ddddd.flip.onboarding.OnBoardingActivity
import ink.ddddd.flip.onboarding.OnBoardingModule
import ink.ddddd.flip.perform.PerformModule
import ink.ddddd.flip.rulebrowse.RuleBrowseModule
import ink.ddddd.flip.ruleedit.RuleEditModule
import ink.ddddd.flip.tagbrowse.TagBrowseModule
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
            RuleEditModule::class,
            RuleBrowseModule::class,
            TagBrowseModule::class,
            TempModule::class]
    )
    abstract fun mainActivity(): MainActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [LauncherModule::class])
    abstract fun launcherActivity(): LauncherActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [OnBoardingModule::class])
    abstract fun onBoardingActivity(): OnBoardingActivity
}