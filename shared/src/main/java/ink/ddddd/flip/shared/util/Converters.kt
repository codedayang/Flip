package ink.ddddd.flip.shared.util

import androidx.room.TypeConverter
import ink.ddddd.flip.shared.filter.Filter
import java.util.*
import kotlin.reflect.KClass

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun kClassToString(kClass: KClass<*>): String {
        return kClass.qualifiedName!!
    }

    @TypeConverter
    fun stringToKClass(string: String): KClass<*> {
        return Class.forName(string).kotlin
    }
}
