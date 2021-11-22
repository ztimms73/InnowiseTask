package org.xtimms.innowise.weather.prefs

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import org.xtimms.innowise.weather.utils.delegates.IntEnumPreferenceDelegate

class AppSettings private constructor(private val prefs: SharedPreferences) :
    SharedPreferences by prefs {

    constructor(context: Context) : this(
        PreferenceManager.getDefaultSharedPreferences(context)
    )

    var defaultSection by IntEnumPreferenceDelegate(
        AppSection::class.java,
        KEY_APP_SECTION,
        AppSection.TODAY
    )

    companion object {
        const val KEY_APP_SECTION = "app_section"
    }

}