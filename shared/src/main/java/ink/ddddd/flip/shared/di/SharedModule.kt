package ink.ddddd.flip.shared.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ink.ddddd.flip.shared.data.CardRepository
import ink.ddddd.flip.shared.data.source.AppDatabase
import ink.ddddd.flip.shared.data.source.CardTagDao
import javax.inject.Singleton

@Module
object SharedModule{
    @Provides
    @Singleton
    fun provideAppDatabase(context: Context) =
        Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()

    @Provides
    @Singleton
    fun provideCardTagDao(appDatabase: AppDatabase) = appDatabase.cardTagDao()

    @Provides
    @Singleton
    fun provideRuleFilterDao(appDatabase: AppDatabase) = appDatabase.ruleFilterDao()

    @Provides
    @Singleton
    fun provideCardRepository(cardTagDao: CardTagDao) = CardRepository(cardTagDao)
}