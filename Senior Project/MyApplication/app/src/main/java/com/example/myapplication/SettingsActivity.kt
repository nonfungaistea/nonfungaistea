package com.example.myapplication

import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference

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

//        val prefs = PreferenceManager
//            .getDefaultSharedPreferences(this)
//        val switch = prefs.getBoolean("switch", false)
//
//        if (switch){
//            Toast.makeText(this, "Dark Mode", Toast.LENGTH_SHORT).show()
//            setTheme(R.style.darkTheme)
//        } else {
//            Toast.makeText(this, "Light Mode", Toast.LENGTH_SHORT).show()
//            setTheme(R.style.lightTheme)
//        }


    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
            val darkModeSwitch: SwitchPreference =
                (findPreference("darkmode") as SwitchPreference?)!!
            darkModeSwitch.onPreferenceChangeListener =
                Preference.OnPreferenceChangeListener { _, _ ->
                    if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    } else {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    }
                    false
                }
        }
    }
}