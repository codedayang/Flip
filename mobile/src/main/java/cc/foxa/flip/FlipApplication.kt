package cc.foxa.flip

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import cc.foxa.flip.di.DaggerAppComponent
import timber.log.Timber

class FlipApplication : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.factory().create(this)
    }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}