package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.BuildCompat
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat

class SettingsActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        val back = findViewById<ImageButton>(R.id.backToSettings)
        back.setOnClickListener { onBackPressed() }
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment())
                .commit()
        }
    }

    class SettingsFragment : PreferenceFragmentCompat() {

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
            val preference: ListPreference =
                (findPreference("pref_key_night") as ListPreference?)!!
//            val currValue: String = preference.value
//            var done = 0
//            if (done == 0 && currValue.equals(R.string.pref_night_on)) {
//                updateTheme(AppCompatDelegate.MODE_NIGHT_NO)
//                done = 1
//            }else if (done == 1 && currValue.equals(R.string.pref_night_on)){
//                updateTheme(AppCompatDelegate.MODE_NIGHT_YES)
//                done = 1
//            }
            preference?.onPreferenceChangeListener = modeChangeListener
        }

        private val modeChangeListener =
            Preference.OnPreferenceChangeListener { _, newValue ->
                Log.i("newValue", newValue.toString())
                newValue as? String
                when (newValue) {
                    getString(R.string.pref_night_on) -> {
                        updateTheme(AppCompatDelegate.MODE_NIGHT_YES)
                    }
                    getString(R.string.pref_night_off) -> {
                        updateTheme(AppCompatDelegate.MODE_NIGHT_NO)
                    }
                    else -> {
                        if (BuildCompat.isAtLeastQ()) {
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                        } else {
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY);
                        }
                    }
                }
                true
            }

        private fun updateTheme(nightMode: Int): Boolean {
            AppCompatDelegate.setDefaultNightMode(nightMode)
            requireActivity().recreate()
            return true
        }
    }
}