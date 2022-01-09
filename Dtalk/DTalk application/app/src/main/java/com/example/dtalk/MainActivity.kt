package com.example.dtalk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.dtalk.App_Open.SignIn_Activity
import com.example.dtalk.Bottom_Menu.Home_Fragment
import com.example.dtalk.Bottom_Menu.Profile_Fragment
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_signin.*

class MainActivity : AppCompatActivity() {

    var temp: Fragment?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)   //will hide the title
        getSupportActionBar()?.hide() //hide the title bar

        setContentView(R.layout.activity_main)
//
        this.getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN) //enable full screen



        //for removing the default color on bottom nav menu....
        bottom_Menu.setItemIconTintList(null)


        //doing the transition to SearchingUser fragment as soon as this activity starts
        supportFragmentManager.beginTransaction().replace(R.id.wrapper, Home_Fragment())
            .commit()


        bottom_Menu.setOnNavigationItemSelectedListener{


            when(it.itemId){

                R.id.Home_menu_button -> {
                    temp = Home_Fragment()
                    supportFragmentManager.beginTransaction().replace(R.id.wrapper, temp as Home_Fragment).commit()

                    true

                }
                R.id.Profile_menu_button -> {
                    temp = Profile_Fragment()
                    supportFragmentManager.beginTransaction().replace(R.id.wrapper, temp as Profile_Fragment).commit()

                    true

                }
                else -> false

            }

        }



    }



}