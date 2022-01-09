package com.example.dtalk.App_Open

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import com.example.dtalk.MainActivity
import com.example.dtalk.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SplashScreen_Activity : AppCompatActivity() {

    lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)   //will hide the title
        getSupportActionBar()?.hide()                   //hide the title bar

        setContentView(R.layout.activity_splash_screen)

        this.getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN) //enable full screen

        mAuth = FirebaseAuth.getInstance()
//        mUser = mAuth.currentUser

        val SPLASH_TIME_OUT: Long = 3000

        val run = Runnable{

            if(mAuth.currentUser != null) {        //means user logged in

                val mRef = FirebaseDatabase.getInstance().getReference().child("Doctors").
                child(mAuth.currentUser!!.uid)
                mRef.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {

                        if (snapshot.exists()){    //means already done profile making activity

                            startActivity(Intent(this@SplashScreen_Activity,MainActivity::class.java))
//                            startActivity(Intent(this@SplashScreen_Activity,SignIn_Activity::class.java))
                            finish()

                        }
                        else{  //if user not done with profile making

                            startActivity(Intent(this@SplashScreen_Activity,Profile_Activity::class.java))
//                            startActivity(Intent(this@SplashScreen_Activity,SignUp_Activity::class.java))
                            finish()

                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(this@SplashScreen_Activity, " Network Error", Toast.LENGTH_SHORT).show()
                    }

                })
            }

            else{
                //if user not logged In
                startActivity(Intent(this,SignIn_Activity::class.java))
                finish()

            }


        }

        Handler().postDelayed(run,SPLASH_TIME_OUT)


    }

}