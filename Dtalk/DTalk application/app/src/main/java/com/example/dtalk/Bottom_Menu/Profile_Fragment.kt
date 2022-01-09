package com.example.dtalk.Bottom_Menu

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.system.Os.remove
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentTransaction
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.dtalk.App_Open.Profile_Activity

import com.example.dtalk.MainActivity
import com.example.dtalk.R

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.android.synthetic.main.activity_after_doctor_selected2.*


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class Profile_Fragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    lateinit var mUser: FirebaseUser
    lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_profile_, container, false)

        val ImageView = v.findViewById<ImageView>(R.id.userBig_Img)
        val UserImage_Profile = v.findViewById<ImageView>(R.id.userSmall_Img)
        val Username_profile = v.findViewById<TextView>(R.id.userName_UserProfile)

        val profession_profile = v.findViewById<TextView>(R.id.profession_name)
        val country_profile = v.findViewById<TextView>(R.id.country_name)
        val city_profile = v.findViewById<TextView>(R.id.city_name)

        val profile1 = v.findViewById<TextView>(R.id.profession_head)
        val profile2 = v.findViewById<TextView>(R.id.country_head)
        val profile3 = v.findViewById<TextView>(R.id.city_head)

        val edit_profile = v.findViewById<TextView>(R.id.edit_profile)





//        val profssion_Profile = v.findViewById<TextView>(R.id.profession_Profile)
        val myCardView = v.findViewById<CardView>(R.id.myCardView)



        edit_profile.setOnClickListener {

            val intent = Intent(context, Profile_Activity::class.java)
            startActivity(intent)

        }

        //for making background of card view transparent
        myCardView.setCardBackgroundColor(Color.TRANSPARENT)

        //elevation of cardview to be 0
        myCardView.setCardElevation(0F)


        mAuth = FirebaseAuth.getInstance()
        mUser = mAuth.currentUser!!


        val mref = FirebaseDatabase.getInstance().getReference().child("Doctors").child(mUser.uid)

        mref.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()){

                    val ImageUrl_profile = snapshot.child("ImageUrl").getValue().toString()
                    val username_profile = snapshot.child("username").getValue().toString()

//                    val profession_profile = snapshot.child("profession").getValue().toString()

                    val profession_profile1 = snapshot.child("profession").getValue().toString()
                    val cityname_profile1 = snapshot.child("cityname").getValue().toString()
                    val countryname_profile1 = snapshot.child("countryname").getValue().toString()



                    //for blur image
                    Glide.with(this@Profile_Fragment).load(ImageUrl_profile).placeholder(R.drawable.user_default_pic)
                        .apply(RequestOptions.bitmapTransform(BlurTransformation(25, 3)))
                        .into(ImageView)


                    Glide.with(this@Profile_Fragment).load(ImageUrl_profile).placeholder(R.drawable.user_default_pic).into(UserImage_Profile)

                    //for profile
                    Username_profile.setText(username_profile)

                    profession_profile.setText(profession_profile1)
                    country_profile.setText(cityname_profile1)
                    city_profile.setText(countryname_profile1)


                }


            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })



        return v
    }

    companion object {


        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Profile_Fragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }

            }

    }

}