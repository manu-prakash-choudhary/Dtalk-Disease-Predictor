package com.example.dtalk.Bottom_Menu

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.dtalk.App_Open.SignIn_Activity
import com.example.dtalk.R
import com.example.dtalk.View_Pager.*
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class Home_Fragment : Fragment() {


    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        val v =  inflater.inflate(R.layout.fragment_home_, container, false)

        val viewPager = v.findViewById<ViewPager>(R.id.view_pager)
        val back_arrow = v.findViewById<ImageButton>(R.id.back_arrow)

        val tabLayout = v.findViewById<TabLayout>(R.id.tab_layout)

        val viewPagerAdapter = ViewPagerAdapter(childFragmentManager)

        viewPagerAdapter.addfragment(Search_Disease_Fragment(), "Search Disease")
        viewPagerAdapter.addfragment(Doctors_Fragment(), "Doctors")
        viewPagerAdapter.addfragment(Chats_Fragment(), "Chats")
//        viewPagerAdapter.addfragment(Medicine_Fragment(), "Buy Medicines")

        viewPager.adapter = viewPagerAdapter
        viewPager.setPageTransformer(true, ZoomOutTransformation())    //added zoom out effect in view pager
        tabLayout.setupWithViewPager(viewPager)

        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser

        back_arrow.setOnClickListener {

            auth.signOut()
            val intent =  Intent(context, SignIn_Activity::class.java)
            startActivity(intent)
            Toast.makeText(context, "Successfully LogOut!!", Toast.LENGTH_SHORT).show()

        }


        return v
    }

    //for view pager making fragment of tab_layouts
    internal class ViewPagerAdapter(fragmentManager: FragmentManager) :
        FragmentPagerAdapter(fragmentManager) {

        //primary constructor
        val fragments: ArrayList<Fragment>

        val titles: ArrayList<String>

        //initializer box
        init {
            fragments = ArrayList()
            titles = ArrayList()
        }

        override fun getCount(): Int {
            return fragments.size
        }

        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }

        fun addfragment(fragment: Fragment, title: String) {

            fragments.add(fragment)
            titles.add(title)
        }

        override fun getPageTitle(i: Int): CharSequence? {
            return titles[i]
        }

    }


    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Home_Fragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }

            }

    }

}