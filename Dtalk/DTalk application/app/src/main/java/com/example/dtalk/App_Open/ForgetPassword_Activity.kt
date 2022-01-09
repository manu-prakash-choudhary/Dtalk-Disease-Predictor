package com.example.dtalk.App_Open

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import com.example.dtalk.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_forget_password.*

class ForgetPassword_Activity : AppCompatActivity() {

    lateinit var mAuth: FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)   //will hide the title
        getSupportActionBar()?.hide()                   //hide the title bar

        setContentView(R.layout.activity_forget_password)

//        this.getWindow().setFlags(
//            WindowManager.LayoutParams.FLAG_FULLSCREEN,
//            WindowManager.LayoutParams.FLAG_FULLSCREEN) //enable full screen

        mAuth = FirebaseAuth.getInstance()
//        mUser = mAuth.currentUser


        verify_button.setOnClickListener {

            ForgetPass()
            verify_button.visibility = View.INVISIBLE

            Toast.makeText(this,"Loading",Toast.LENGTH_SHORT).show()


        }

        back_arrow.setOnClickListener {

            startActivity(Intent(this, SignIn_Activity::class.java))
            finish()

        }



    }

    private fun ForgetPass() {

        val inputEmail = forget_email_edittext.text.toString().trim()

        if(inputEmail.isEmpty()){
            forget_email_edittext.error = "Empty!!"

        }

        else{

            mAuth.sendPasswordResetEmail(inputEmail).addOnSuccessListener {

                Toast.makeText(this,"Please Check your Email", Toast.LENGTH_LONG).show()
                verify_button.visibility = View.VISIBLE
            }
                .addOnFailureListener{

                    Toast.makeText(this,"Error", Toast.LENGTH_LONG).show()
                    verify_button.visibility = View.VISIBLE
                }


        }

    }
}