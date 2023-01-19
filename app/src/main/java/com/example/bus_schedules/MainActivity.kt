package com.example.bus_schedules

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnFragmentChangeListener {

    private lateinit var currentFragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_container)

        currentFragment = when (this.checkAppStart()) {
            AppStart.NORMAL -> {
                MapViewFragment()
            }
            AppStart.FIRST_TIME_VERSION -> {
                InitFragment()
            }
            AppStart.FIRST_TIME -> {
                InitFragment()
            }
        }

        showFragment(currentFragment)
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    @Override
    override fun onFragmentChange(fragment: Fragment) {
        showFragment(fragment)
    }

}