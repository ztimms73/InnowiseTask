package org.xtimms.innowise.weather.utils.delegates

import android.content.SharedPreferences
import androidx.core.content.edit
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

@Deprecated("")
class IntEnumPreferenceDelegate<E : Enum<*>>(
    private val cls: Class<E>,
    private val key: String,
    private val defValue: E
) : ReadWriteProperty<SharedPreferences, E> {

    override fun getValue(thisRef: SharedPreferences, property: KProperty<*>): E {
        val ord = thisRef.getInt(key, -1)
        if (ord == -1) {
            return defValue
        }
        return cls.enumConstants?.getOrNull(ord) ?: defValue
    }

    override fun setValue(thisRef: SharedPreferences, property: KProperty<*>, value: E) {
        thisRef.edit {
            putInt(key, value.ordinal)
        }
    }
}