package ink.ddddd.flip

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import ink.ddddd.flip.di.DaggerAppComponent
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