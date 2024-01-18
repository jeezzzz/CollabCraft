package com.jeezzzz.collabcraft

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class SecondActivity : AppCompatActivity() {
    private val homeFragment = HomeFragment()
    private val communityFragment = CommunityFragment()
    private val addFragment = AddFragment()
    private val bellFragment = BellFragment()
    private val profileFragment = ProfileFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigation)


        // Set the HomeFragment as the initial fragment
        setCurrentFragment(homeFragment)

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home->setCurrentFragment(homeFragment)
//                R.id.nav_community->setCurrentFragment(communityFragment)
                R.id.nav_add->setCurrentFragment(addFragment)
//                R.id.nav_bell->setCurrentFragment(bellFragment)
                R.id.nav_profile->setCurrentFragment(profileFragment)
            }
            true
        }
    }
    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainer, fragment)
            commit()
        }
    }
}