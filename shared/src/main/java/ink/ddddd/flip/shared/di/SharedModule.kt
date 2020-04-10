package ink.ddddd.flip.shared.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ink.ddddd.flip.shared.data.source.AppDatabase

@Module
object SharedModule{
    @Provides
    fun provideAppDatabase(context: Context) =
        Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()

    @Provides
    fun provideCardTagDao(appDatabase: AppDatabase) = appDatabase.cardTagDao()

    @Provides
    fun provideRuleFilterDao(appDatabase: AppDatabase) = appDatabase.ruleFilterDao()
}