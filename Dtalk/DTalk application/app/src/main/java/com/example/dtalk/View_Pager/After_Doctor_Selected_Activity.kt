package com.example.dtalk.View_Pager

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.dtalk.MainActivity
import com.example.dtalk.Model_Class.Doctors_
import com.example.dtalk.R
import com.example.dtalk.Selected_Doctor.Chat_Log_Activity
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.android.synthetic.main.activity_after_doctor_selected2.*

class After_Doctor_Selected_Activity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_after_doctor_selected2)

//        this.getWindow().setFlags(
//            WindowManager.LayoutParams.FLAG_FULLSCREEN,
//            WindowManager.LayoutParams.FLAG_FULLSCREEN)

        val selectedDoc = intent.getParcelableExtra<Doctors_>(Doctors_Fragment.Doc_key)

//        val selectedDoc_img =  intent.getParcelableExtra<Doctors_>(Doctors_Fragment.Doc_key)



        //for making background of card view transparent
        myCardView.setCardBackgroundColor(Color.TRANSPARENT)


        //elevation of cardview to be 0
        myCardView.setCardElevation(0F)


        back_arrow.setOnClickListener {

            startActivity(Intent(this, MainActivity::class.java))
            finish()

        }

        //setting images on Doctors Profile n names n profession

        if (selectedDoc!=null) {


            //for big image
            Glide.with(this).load(selectedDoc.ImageUrl).placeholder(R.drawable.user_default_pic)
                .apply(RequestOptions.bitmapTransform(BlurTransformation(25, 3))).into(docBig_Img)

            //for small image
            //setting images on Doctors Profile
            Glide.with(this).load(selectedDoc.ImageUrl).placeholder(R.drawable.user_default_pic).into(docSmall_Img)

            //adding name of selected doctor
            DocName_UserProfile.text = selectedDoc.username

            //adding profession of selected doctor
            DocProfession_UserProfile.text = selectedDoc.profession


            About_button.setOnClickListener {

//                val userIntent = Intent(this, ::class.java)
//                userIntent.putExtra(aboutKey,selectedDoc)
//                startActivity(userIntent)

            }

            Message_button.setOnClickListener {


                val userIntent = Intent(this, Chat_Log_Activity::class.java)
                userIntent.putExtra(chatKey,selectedDoc)
                startActivity(userIntent)

            }

        }


    }

    companion object{

        val chatKey = "chat_key"
        val aboutKey = "about_key"
    }


}