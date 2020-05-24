package cc.foxa.flip.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import cc.foxa.flip.MainActivity
import cc.foxa.flip.cardbrowse.CardBrowseModule
import cc.foxa.flip.cardedit.CardEditModule
import cc.foxa.flip.launcher.LauncherActivity
import cc.foxa.flip.launcher.LauncherModule
import cc.foxa.flip.onboarding.OnBoardingActivity
import cc.foxa.flip.onboarding.OnBoardingModule
import cc.foxa.flip.perform.PerformModule
import cc.foxa.flip.rulebrowse.RuleBrowseModule
import cc.foxa.flip.ruleedit.RuleEditModule
import cc.foxa.flip.tagbrowse.TagBrowseModule
import cc.foxa.flip.tagedit.TagEditModule
import cc.foxa.flip.temp.TempModule

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