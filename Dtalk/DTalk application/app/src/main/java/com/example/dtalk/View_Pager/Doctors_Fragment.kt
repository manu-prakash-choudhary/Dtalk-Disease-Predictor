package com.example.dtalk.View_Pager

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Rect
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dtalk.Model_Class.Doctors_
import com.example.dtalk.R
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_chat_log.*
import kotlinx.android.synthetic.main.doctors_recyler_item.view.*
import kotlinx.android.synthetic.main.fragment_doctors_.*
import kotlinx.android.synthetic.main.fragment_search__disease_.*

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener as FirebaseDatabaseValueEventListener


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class Doctors_Fragment : Fragment() {

    private lateinit var ref: DatabaseReference
    private lateinit var myRecycler: RecyclerView
    private lateinit var mUser: FirebaseUser
    private lateinit var mAuth: FirebaseAuth
//    lateinit var firebaseRecyclerAdapter: FirebaseRecyclerAdapter<Doctors_, MyUsersviewHolder>


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

        val v = inflater.inflate(R.layout.fragment_doctors_, container, false)

//        val SearchBox = v.findViewById<EditText>(R.id.search_Doc)
//        val search_Doc_button = v.findViewById<Button>(R.id.search_Doc_button)

        val searchBox = v.findViewById<SearchView>(R.id.searchBox)

        val search_bar_card = v.findViewById<CardView>(R.id.search_bar_card)


        ref = FirebaseDatabase.getInstance().getReference().child("Doctors")
        mAuth = FirebaseAuth.getInstance()
        mUser = mAuth.currentUser!!

        myRecycler = v.findViewById<RecyclerView>(R.id.Search_User_recyclerView)
        myRecycler.setHasFixedSize(true)
        myRecycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        myRecycler.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL))

        //for making background of card view transparent
        search_bar_card.setCardBackgroundColor(Color.TRANSPARENT)
        //elevation of cardview to be 0
        search_bar_card.setCardElevation(1F)



//        <EditText
//        android:id="@+id/search_Doc"
//        android:layout_width="@dimen/_250sdp"
//        android:layout_margin="@dimen/_2sdp"
//        android:layout_height="@dimen/_25sdp"
//        android:background="@drawable/messaging_bar"/>
//
//        <Button
//        android:id="@+id/search_Doc_button"
//        android:layout_width="30dp"
//        android:layout_height="30dp"
//        android:layout_marginLeft="@dimen/_10sdp"
//        android:layout_marginTop="@dimen/_5sdp"
//        android:background="@drawable/search_img"
//        android:layout_toEndOf="@+id/search_Doc"/>

//        myRecycler.scrollToPosition(madapter.itemCount - 1)



        var shouldSearch = false

        searchBox.setOnQueryTextFocusChangeListener { view, hasFocus ->

            if (hasFocus) {
                shouldSearch = true
            } else {
                shouldSearch = false
            }

        }

        //getting the bundle value which is passed from Search_Disease_Fragmnet()

        val bundle: Bundle? = this.arguments
        val data: String? = bundle?.getString(Search_Disease_Fragment.MyKey)
        if (data != null) {
            Firebase_recommended_Doc_Search(data,context)
        }






//        val InputText = searchBox.text.toString()
//        SearchBox.setQuery(data, true )
//        search_Doc_button.setOnClickListener {
//
//
//        }

//        Firebase_recommended_Doc_Search()

////        SearchBox.


//        val query: Query = FirebaseDatabase.getInstance().getReference().child("Doctor").orderByChild("username")
//           .equalTo(data)
//
//            query.addListenerForSingleValueEvent(object: FirebaseDatabaseValueEventListener {
//                override fun onDataChange(snapshot: DataSnapshot) {
//
//                    if(snapshot.exists()){
//
//                        for(x in snapshot.children){
//
//                            val item = x.getValue(Doctors_::class.java)
//                            val Myarray= ArrayList<String>()
//                            Myarray.add(item.toString())
//                            Firebase_recommended_Doc_Search(Myarray,context)
//
//                        }
//
//
//                    }
//
//                }
//
//                override fun onCancelled(error: DatabaseError) {
//                    TODO("Not yet implemented")
//                }
//
//            })



        searchBox.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {

                Firebase_InputText_Doc_Search(query, context)
                return false

            }

            override fun onQueryTextChange(newText: String?): Boolean {

                Firebase_InputText_Doc_Search(newText,context)
                return true

            }

        })

//        fetchDoctors()


        return v

    }



    private fun Firebase_InputText_Doc_Search(searchText: String?, mContext: Context?) {


        val FirebaseUsers: Query = ref.orderByChild("username").startAt(searchText).endAt(searchText + "\uf0ff")

        val options = FirebaseRecyclerOptions.Builder<Doctors_>()
            .setQuery(FirebaseUsers, Doctors_::class.java)
            .build()

        val firebaseRecyclerAdapter =
            object : FirebaseRecyclerAdapter<Doctors_, MyUsersviewHolder>(options) {

                override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyUsersviewHolder
                {
                    val myView = LayoutInflater.from(parent.context).inflate(R.layout.doctors_recyler_item, parent, false)

                    return MyUsersviewHolder(myView)
                }

                override fun onBindViewHolder(holder: MyUsersviewHolder, position: Int, model: Doctors_)
                {

                    if (!mUser.uid.equals(getRef(position).key.toString())) {

                        holder.User_name.text = model.username

                        if (mContext != null) {
                            Glide.with(mContext).load(model.ImageUrl).centerCrop().into(holder.profile_image)

                        }

                        holder.itemView.setOnClickListener {

                            val userIntent = Intent(context, After_Doctor_Selected_Activity::class.java)
                            userIntent.putExtra(Doc_key, model)
                            startActivity(userIntent)

                        }

                    }
                    else {

                        //if search matches with current username then it won't show the results...
                        holder.itemView.visibility = View.GONE
                        holder.itemView.layoutParams = RecyclerView.LayoutParams(0, 0)

                    }

                }

            }

        val ladapter = firebaseRecyclerAdapter

        myRecycler.adapter = ladapter

        myRecycler.scrollToPosition(ladapter.itemCount - 1)

        ladapter.startListening()

    }

//    startAt(recommendedDoc_name.toString()).endAt(recommendedDoc_name.toString() + "\uf0ff")

    private fun Firebase_recommended_Doc_Search(recommendedDoc_name: String, mContext: Context?) {

        val FirebaseUsers: Query = ref.orderByChild("username").equalTo(recommendedDoc_name)

        val options = FirebaseRecyclerOptions.Builder<Doctors_>()
            .setQuery(FirebaseUsers, Doctors_::class.java)
            .build()

        val firebaseRecyclerAdapter = object : FirebaseRecyclerAdapter<Doctors_, MyDoc_viewHolder>(options) {

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyDoc_viewHolder {

                val myView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.trial_recommended_doc_single_tem , parent, false)

                return MyDoc_viewHolder(myView)

            }

            override fun onBindViewHolder(holder: MyDoc_viewHolder, position: Int, model: Doctors_) {

                if (!mUser.uid.equals(getRef(position).key.toString())) {

                    holder.User_name2.text = model.username

                    if (mContext != null) {

                        Glide.with(mContext).load(model.ImageUrl).centerCrop().into(holder.profile_image2)

                    }

                    holder.itemView.setOnClickListener {

                        val userIntent = Intent(context, After_Doctor_Selected_Activity::class.java)
                        userIntent.putExtra(Doc_key, model)
                        startActivity(userIntent)

                    }

                } else {

                    //if search matches with current username then it won't show the results...
                    holder.itemView.visibility = View.GONE
                    holder.itemView.layoutParams = RecyclerView.LayoutParams(0, 0)

                }


            }


        }

        val ladapter = firebaseRecyclerAdapter
        myRecycler.adapter = ladapter

        myRecycler.scrollToPosition(ladapter.itemCount - 1)
        ladapter.startListening()

    }



    companion object {
          @JvmStatic

         val Doc_key = "DocKey"


        fun newInstance(param1: String, param2: String) =
            Doctors_Fragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }


            }

    }


}

class MyDoc_viewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val User_name2 = itemView.findViewById<TextView>(R.id.doc_name2)

    val profile_image2= itemView.findViewById<ImageView>(R.id.doc_image2)

}


class MyUsersviewHolder (itemView: View): RecyclerView.ViewHolder(itemView){

    val User_name = itemView.findViewById<TextView>(R.id.doc_name)

    val profile_image = itemView.findViewById<ImageView>(R.id.doc_image)

}


