package ink.ddddd.flip.shared.di

import android.content.Context
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import dagger.Module
import dagger.Provides
import ink.ddddd.flip.shared.data.source.AppDatabase

@Module
object TestModule {
    @Provides
    fun provideAppContext(): Context = InstrumentationRegistry.getInstrumentation().targetContext

    @Provides
    fun provideAppDatabase(context: Context) =
        Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()

    @Provides
    fun provideCardTagDao(appDatabase: AppDatabase) = appDatabase.cardTagDao()
}