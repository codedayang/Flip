package cc.foxa.flip.shared.di

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import dagger.Module
import dagger.Provides
import cc.foxa.flip.shared.data.source.AppDatabase

@Module
object TestModule {
    @Provides
    fun provideAppContext(): Context = InstrumentationRegistry.getInstrumentation().targetContext
}