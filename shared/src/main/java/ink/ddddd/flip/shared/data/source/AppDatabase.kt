package ink.ddddd.flip.shared.data.source

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ink.ddddd.flip.shared.data.model.*
import ink.ddddd.flip.shared.util.Converters

@Database(
    entities = [Card::class, Tag::class, CardTagJoin::class, Rule::class, FilterBean::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cardTagDao(): CardTagDao
    abstract fun ruleFilterDao(): RuleFilterDao
}