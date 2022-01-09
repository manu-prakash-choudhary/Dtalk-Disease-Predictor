package com.example.dtalk.App_Open

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import com.example.dtalk.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_signin.*
import kotlinx.android.synthetic.main.activity_signup.*

class SignUp_Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)   //will hide the title
        getSupportActionBar()?.hide()                   //hide the title bar

        setContentView(R.layout.activity_signup)

        this.getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN) //enable full screen
//
//        //for making background of card view transparent
//        signup_email_card.setCardBackgroundColor(Color.TRANSPARENT)
//        //elevation of cardview to be 0
//        signup_email_card.setCardElevation(1F)
//
//
//        //for making background of card view transparent
//        signup_custom_card.setCardBackgroundColor(Color.TRANSPARENT)
//        //elevation of cardview to be 0
//        signup_custom_card.setCardElevation(1F)
//
//
//        //for making background of card view transparent
//        signup_pass_card.setCardBackgroundColor(Color.TRANSPARENT)
//        //elevation of cardview to be 0
//        signup_pass_card.setCardElevation(1F)
//
//
//        //for making background of card view transparent
//        signup_confirpass_card.setCardBackgroundColor(Color.TRANSPARENT)
//        //elevation of cardview to be 0
//        signup_confirpass_card.setCardElevation(1F)
//


        //switching to the Signin page if already a user
        already_user.setOnClickListener {

            startActivity(Intent(applicationContext,SignIn_Activity::class.java))
            finish()

        }

        //Signup button click start with registration
        SignUp_button.setOnClickListener {
            performRegister()
            SignUp_button.visibility = View.INVISIBLE

            Toast.makeText(this,"Loading",Toast.LENGTH_SHORT).show()


        }

    }

    private fun performRegister() {
        val email = register_email_id_edittext.text.toString().trim()
        val password = register_password_editetext.text.toString().trim()
        val Confirm_password = confirm_password_editext.text.toString().trim()

        if (email.isEmpty() ) {

            register_email_id_edittext.error = "Empty!!"
            return
        }
        else if (!email.contains("@")){
            register_email_id_edittext.error = "not Valid Email ID"
            return
        }
        else if (password.isEmpty()) {
            register_password_editetext.error = "Empty!!"
            return
        }
        else if (Confirm_password.isEmpty()){
            confirm_password_editext.error = "Empty!!"
            return

        }

        else if (!Confirm_password.equals(password)){
            confirm_password_editext.error = "Password is not similar, Try again"
        }

        else{
            custom_loading.visibility=View.VISIBLE

        }

        Log.d("SignUPActivity", "Attempting to create user with email: $email")

        // Firebase Authentication to create a user with email and password
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (!it.isSuccessful) return@addOnCompleteListener

                //showing custom progress bar
                custom_loading.visibility=View.INVISIBLE


                val intent = Intent(this, Profile_Activity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK. or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()

                // else if successful
                Log.d("SignUPActivity", "Successfully created user with uid: ${it.result?.user?.uid}")

            }
            .addOnFailureListener{

                custom_loading.visibility=View.INVISIBLE
                SignUp_button.visibility = View.VISIBLE

                Log.d("SignUPActivity", "Failed to create user: ${it.message}")
                Toast.makeText(this, "Failed to create user: ${it.message}", Toast.LENGTH_SHORT).show()
            }


    }

}