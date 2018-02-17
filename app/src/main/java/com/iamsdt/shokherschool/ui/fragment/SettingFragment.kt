package com.iamsdt.shokherschool.ui.fragment

import android.content.SharedPreferences
import android.os.Bundle
import android.support.v14.preference.SwitchPreference
import android.support.v7.preference.CheckBoxPreference
import android.support.v7.preference.ListPreference
import android.support.v7.preference.Preference
import android.support.v7.preference.PreferenceFragmentCompat
import com.iamsdt.shokherschool.R



/**
 * Created by Shudipto Trafder on 1/14/2018.
 * at 11:08 PM
 */
class SettingsFragment: PreferenceFragmentCompat(),
        SharedPreferences.OnSharedPreferenceChangeListener{

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        // Add 'general' preferences, defined in the XML file
        addPreferencesFromResource(R.xml.pref_general)

        val sharedPreferences = preferenceScreen.sharedPreferences

        val count = preferenceScreen.preferenceCount

        for (i in 0 until count) {
            val p = preferenceScreen.getPreference(i)
            when (p) {
                is CheckBoxPreference -> {
                    val value = sharedPreferences.getBoolean(p.key, false)
                    setPreferenceSummery(p, value)
                }
                is SwitchPreference -> {
                    val value = sharedPreferences.getBoolean(p.key, false)
                    setPreferenceSummery(p, value)
                }
                else -> {
                    val value = sharedPreferences.getString(p.key, "")
                    setPreferenceSummery(p, value)
                }
            }
        }
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {

        val preference = findPreference(key)

        if (preference != null) {

            if (preference !is CheckBoxPreference) {
                val value = sharedPreferences?.getString(preference.key, "") ?: ""
                setPreferenceSummery(preference, value)
            }
        }
    }

    private fun setPreferenceSummery(preference: Preference, value: Any) {

        val stringValue = value.toString()

        if (preference is ListPreference) {

            val prefIndex = preference.findIndexOfValue(stringValue)

            if (prefIndex >= 0) {
                preference.summary = preference.entries[prefIndex]
            }
        } else {
            // For other preferences, set the summary to the value's simple string representation.
            preference.summary = stringValue
        }
    }

    override fun onStart() {
        super.onStart()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }
}