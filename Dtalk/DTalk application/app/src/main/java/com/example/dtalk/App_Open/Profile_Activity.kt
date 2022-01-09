package com.example.dtalk.App_Open

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
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
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_signin.*
import java.util.*
import kotlin.collections.HashMap

class Profile_Activity : AppCompatActivity() {

    lateinit var mAuth: FirebaseAuth
    lateinit var mUser: FirebaseUser


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)   //will hide the title
        getSupportActionBar()?.hide()                   //hide the title bar

        setContentView(R.layout.activity_profile)

//        this.getWindow().setFlags(
//            WindowManager.LayoutParams.FLAG_FULLSCREEN,
//            WindowManager.LayoutParams.FLAG_FULLSCREEN) //enable full screen




//
//        //for making background of card view transparent
//        user_profile_card.setCardBackgroundColor(Color.TRANSPARENT)
//        //elevation of cardview to be 0
//        user_profile_card.setCardElevation(1F)
//
//
//        //for making background of card view transparent
//        profile_custom_card.setCardBackgroundColor(Color.TRANSPARENT)
//        //elevation of cardview to be 0
//        profile_custom_card.setCardElevation(1F)
//
//
//
//        //for making background of card view transparent
//        cityname_profile_card.setCardBackgroundColor(Color.TRANSPARENT)   //elevation of cardview to be 0
//        cityname_profile_card.setCardElevation(1F)
//
//
//        //for making background of card view transparent
//        countryName_profile_card.setCardBackgroundColor(Color.TRANSPARENT)
//        //elevation of cardview to be 0
//        countryName_profile_card.setCardElevation(1F)
//
//
//        //for making background of card view transparent
//        Profession_profile_card.setCardBackgroundColor(Color.TRANSPARENT)
//        //elevation of cardview to be 0
//        Profession_profile_card.setCardElevation(1F)
//
//
//



        circular_image.setOnClickListener {

            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 1)

        }

        Already_user_made_profile.setOnClickListener {

            startActivity(Intent(applicationContext, MainActivity::class.java))
            finish()

        }

        mAuth = FirebaseAuth.getInstance()
        mUser = mAuth.currentUser!!

    }

    //Bringing image from gallery to the App
    private lateinit var imageURI: Uri


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        //for picking images
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null) {
            Log.d("OTP_", "Image Result Received")

            //geting the URI(location) of the image
            imageURI = data.data!!


            //setting the profile image selected in circular format
            circular_image.setImageURI(imageURI)


            //checking the ImageURI shouldn't be null and then calling the function uploadingImageinFireBaseStorage()
            Save_button.setOnClickListener {

                if (imageURI != null) {

                    Save_button.visibility = View.INVISIBLE
                    SavingData(this, imageURI)
                    Toast.makeText(this,"Loading",Toast.LENGTH_SHORT).show()


                }

            }

        }

    }


    //uploading Profile data in firebase storage
    fun SavingData(mContext: Context, imageURI: Uri) {

        //taking the value from the EditText which named as Myname in text format
        val username = username_editText.text.toString().trim()
        val countryname = countryName_editText.text.toString().trim()
        val cityname = cityName_editText.text.toString().trim()
        val profession = Profession_editText.text.toString().trim()

        if (username.isEmpty()){

            username_editText.error = "Empty!!"
            return
        }
        else if(countryname.isEmpty()){

            countryName_editText.error = "Empty!!"
            return
        }
        else if(cityname.isEmpty()){

            cityName_editText.error = "Empty!!"
            return
        }
        else if(profession.isEmpty()){

            Profession_editText.error = "Empty!!"
            return
        }
        else{

            val filePath = UUID.randomUUID().toString()

            val Storageref = FirebaseStorage.getInstance().getReference("/patient Profile Image/$filePath").child(mUser.uid)

            Storageref.putFile(imageURI)
                .addOnSuccessListener {

                    Storageref.downloadUrl.addOnSuccessListener {
                        val UserProfileUrl = it.toString()

                        val myHashmap = HashMap<String,Any>()
                        myHashmap.put("uid",mUser.uid)
                        myHashmap.put("username",username)
                        myHashmap.put("ImageUrl", UserProfileUrl)
                        myHashmap.put("countryname",countryname)
                        myHashmap.put("cityname",cityname)
                        myHashmap.put("profession",profession)

//                        App Users

                        val ref = FirebaseDatabase.getInstance().getReference().child("Doctors").child(mUser.uid)
                        ref.updateChildren(myHashmap)

                            .addOnSuccessListener {

                                Log.d("Profile_Activity", "Profile saved")
                                Toast.makeText(this, "Profile data Saved",Toast.LENGTH_LONG).show()

                                startActivity(Intent(this, MainActivity::class.java))
                                finish()

                            }
                            .addOnFailureListener {

                                Log.d("Profile_Activity", "Profile not saved")
                                Toast.makeText(this, "Profile data not Saved",Toast.LENGTH_LONG).show()
                                Save_button.visibility = View.VISIBLE
                            }

                    }


                        .addOnFailureListener {
                            Log.d("Profile_Activity", "Error in db")
                            Toast.makeText(this, "Error!! Profile data not Saved",Toast.LENGTH_LONG).show()

                            Save_button.visibility = View.VISIBLE


                        }

                }

        }

    }




}