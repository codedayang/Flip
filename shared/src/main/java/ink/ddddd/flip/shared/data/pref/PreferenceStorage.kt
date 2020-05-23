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
    var cardEditHelperShown: Boolean
    var ruleSelectHelperShown: Boolean
}

@Singleton
class SharedPreferenceStorage @Inject constructor(context: Context) : PreferenceStorage {
    private val prefs: Lazy<SharedPreferences> = lazy {
        context.applicationContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    override var onBoardingCompleted by BooleanPreference(prefs, PREF_ONBOARDING, false)

    override var cardEditHelperShown by BooleanPreference(prefs, PREF_CARD_EDIT_HELPER_SHOWN, false)

    override var ruleSelectHelperShown: Boolean by BooleanPreference(prefs, PREF_RULE_SELECT_HELPER_SHOWN, false)

    companion object {
        const val PREF_NAME = "flip"
        const val PREF_ONBOARDING = "pref_onboarding"
        const val PREF_CARD_EDIT_HELPER_SHOWN = "pref_card_edit_helper_shown"
        const val PREF_RULE_SELECT_HELPER_SHOWN = "pref_rule_select_helper_shwon"
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