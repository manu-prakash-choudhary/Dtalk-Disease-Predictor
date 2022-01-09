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
import com.example.dtalk.MainActivity
import com.example.dtalk.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_signin.*

class SignIn_Activity : AppCompatActivity() {


    val mAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)   //will hide the title
        getSupportActionBar()?.hide()                   //hide the title bar

        setContentView(R.layout.activity_signin)

//        this.getWindow().setFlags(
//            WindowManager.LayoutParams.FLAG_FULLSCREEN,
//            WindowManager.LayoutParams.FLAG_FULLSCREEN) //enable full screen


//        //for making background of card view transparent
//        signin_emai_card.setCardBackgroundColor(Color.TRANSPARENT)
//
//        //elevation of cardview to be 0
//        signin_emai_card.setCardElevation(1F)
//
//        //for making background of card view transparent
//        custom_signin_card.setCardBackgroundColor(Color.TRANSPARENT)
//        //elevation of cardview to be 0
//        custom_signin_card.setCardElevation(1F)
//
//
//
//        //for making background of card view transparent
//        signin_pass_card.setCardBackgroundColor(Color.TRANSPARENT)
//
//        //elevation of cardview to be 0
//        signin_pass_card.setCardElevation(1F)


        not_registered.setOnClickListener {
            startActivity(Intent(applicationContext,SignUp_Activity::class.java))
            finish()

        }

        forget_password_textView.setOnClickListener {

            startActivity(Intent(this, ForgetPassword_Activity::class.java))
            finish()
        }


        Signin_button.setOnClickListener{
            performLogin()
            Signin_button.visibility = View.INVISIBLE
            signin_button_card.visibility = View.INVISIBLE

            Toast.makeText(this,"Loading",Toast.LENGTH_SHORT).show()




        }

    }

    private fun performLogin() {
        val email = enter_email_id_edittext.text.toString().trim()
        val password = enter_password_edittext.text.toString().trim()

        if (email.isEmpty() || password.isEmpty()) {
            enter_email_id_edittext.error = "Empty!!"
            enter_password_edittext.error = "Empty!!"
        }

        else if(!email.contains("@")){
            enter_email_id_edittext.error ="Invalid Email ID"
        }

        else{
            custom_loading2.visibility = View.VISIBLE
        }

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (!it.isSuccessful) return@addOnCompleteListener

                custom_loading2.visibility = View.INVISIBLE
                Log.d("Login", "Successfully logged in: ${it.result?.user?.uid}")
//                val intent = Intent(this,Profile_Activity::class.java)
//                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
//                startActivity(intent)


                if(mAuth.currentUser != null) {        //means user logged in

                    val mRef = FirebaseDatabase.getInstance().getReference().child("Doctors").
                    child(mAuth.currentUser!!.uid)
                    mRef.addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {

                            if (snapshot.exists()){    //means already done profile making activity

                                startActivity(Intent(this@SignIn_Activity, MainActivity::class.java))
//                            startActivity(Intent(this@SplashScreen_Activity,SignIn_Activity::class.java))
                                finish()

                            }
                            else{  //if user not done with profile making

                                val intent = Intent(this@SignIn_Activity,Profile_Activity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                                startActivity(intent)
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            Toast.makeText(this@SignIn_Activity, " Network Error", Toast.LENGTH_SHORT).show()
                        }

                    })
                }



            }
            .addOnFailureListener {

                custom_loading2.visibility = View.INVISIBLE
                Signin_button.visibility = View.VISIBLE
                Toast.makeText(this, "Failed to log in: ${it.message}", Toast.LENGTH_SHORT).show()
            }


    }
}