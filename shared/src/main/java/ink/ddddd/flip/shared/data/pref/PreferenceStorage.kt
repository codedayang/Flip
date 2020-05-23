package ink.ddddd.flip.shared.data.pref

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

interface PreferenceStorage {
    var onBoardingCompleted: Boolean
}

@Singleton
class SharedPreferenceStorage @Inject constructor(context: Context) : PreferenceStorage {
    private val prefs: Lazy<SharedPreferences> = lazy {
        context.applicationContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    override var onBoardingCompleted by BooleanPreference(prefs, PREF_ONBOARDING, false)

    companion object {
        const val PREF_NAME = "flip"
        const val PREF_ONBOARDING = "pref_onboarding"
    }
}

class BooleanPreference(
    private val prefs: Lazy<SharedPreferences>,
    private val name: String,
    private val defaultValue: Boolean
) : ReadWriteProperty<Any,Boolean> {
    override fun getValue(thisRef: Any, property: KProperty<*>): Boolean {
        return prefs.value.getBoolean(name, defaultValue)
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: Boolean) {
        prefs.value.edit {
            putBoolean(name, value)
        }
    }

}