package com.mmm.buget_trackerapp.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mmm.buget_trackerapp.Fragment.AddFragment
import com.mmm.buget_trackerapp.Fragment.SumrrayFragment
import com.mmm.buget_trackerapp.Fragment.TractionFragment
import com.mmm.buget_trackerapp.R
import com.mmm.buget_trackerapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    val AddFragment = AddFragment()
    val SumrrayFragment = SumrrayFragment()
    val TractionFragment = TractionFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.btnsubmit2)



        replaceFragment(SumrrayFragment)

        bottomNavigationView.setOnItemSelectedListener {

            when (it.itemId) {

                R.id.ic_btnsubmit -> replaceFragment(SumrrayFragment)
                R.id.ic_Add -> replaceFragment(AddFragment)
                R.id.ic_traction -> replaceFragment(TractionFragment)

            }
            true

        }


    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragpageview,fragment).commit()

    }

}