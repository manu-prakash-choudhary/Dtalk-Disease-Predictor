package com.example.dtalk.View_Pager

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dtalk.Model_Class.ChatlogUsersList
import com.example.dtalk.Model_Class.Doctors_
import com.example.dtalk.Model_Class.appUsers
import com.example.dtalk.R
import com.example.dtalk.Selected_Doctor.Chat_Log_Activity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.chats_latest_single_row.view.*


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class Chats_Fragment : Fragment() {


    lateinit var mUser: FirebaseUser
    lateinit var mAuth: FirebaseAuth
    lateinit var recycler: RecyclerView
    val madapter = GroupAdapter<GroupieViewHolder>()

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
        val v =  inflater.inflate(R.layout.fragment_chats_, container, false)


        mAuth = FirebaseAuth.getInstance()
        mUser = mAuth.currentUser!!

        recycler = v.findViewById(R.id.recentChat_recycler)
        recycler.setHasFixedSize(true)
        recycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        madapter.setOnItemClickListener { item, view ->

            val row = item as Chatmessage

            val intent = Intent(context,Chat_Log_Activity::class.java)
            intent.putExtra(After_Doctor_Selected_Activity.chatKey,row.chatPartnerUser)
            startActivity(intent)

        }

//        madapter.setOnItemClickListener { item, view ->
//
//            val row = item as Chatmessage
//
//            val intent = Intent(context,Chat_Log_Activity::class.java)
//            intent.putExtra(Doctors_Fragment.Doc_key,row.chatPartnerUser2)
//            startActivity(intent)
//
//        }

        recycler.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        DisplayrecentChat()

        recycler.adapter = madapter

        return v

    }

    val latestmessagemap = HashMap<String, ChatlogUsersList>()

    private fun refreshLatestMessages(){

        madapter.clear()
        latestmessagemap.values.forEach {

            madapter.add(Chatmessage(this,it))

        }

    }

    private fun DisplayrecentChat() {


        val recentChatRef = FirebaseDatabase.getInstance().getReference("/Recent Chats/${mUser.uid}")
        recentChatRef.addChildEventListener(object : ChildEventListener {

            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {

                if (snapshot.exists()) {

                    val chats = snapshot.getValue(ChatlogUsersList::class.java) ?: return
                    latestmessagemap[snapshot.key!!] = chats
                    refreshLatestMessages()

                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {

                if (snapshot.exists()){

                    val chats = snapshot.getValue(ChatlogUsersList::class.java) ?:return
                    latestmessagemap[snapshot.key!!] = chats
                    refreshLatestMessages()

                }

            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                TODO("Not yet implemented")
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }


    companion object {
              @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Chats_Fragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

}


class Chatmessage(val context: Chats_Fragment, val user: ChatlogUsersList) : Item<GroupieViewHolder>() {

    var chatPartnerUser: Doctors_? = null
    //we will use this for showing the msg in the chatlog when we go to chat log via recent chats section

    var chatPartnerUser2: appUsers?=null

    @SuppressLint("SetTextI18n")
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {


        viewHolder.itemView.recentChats.text = user.Chats

        val chatpartnerId: String
        if (user.SenderId .equals(FirebaseAuth.getInstance().uid) ){

            chatpartnerId = user.ReceiverId!!

        }

        else{

            chatpartnerId = user.SenderId!!

        }

        val ref = FirebaseDatabase.getInstance().getReference("/Doctors/$chatpartnerId")
        ref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()){

                    chatPartnerUser = snapshot.getValue(Doctors_::class.java)

                    Glide.with(context).load(chatPartnerUser!!.ImageUrl).into(viewHolder.itemView.recent_chat_User_image)

                    viewHolder.itemView.recent_chats_username.text = chatPartnerUser!!.username

                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        val mref = FirebaseDatabase.getInstance().getReference("/App Users/$chatpartnerId")
        mref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()){

                    chatPartnerUser2= snapshot.getValue(appUsers::class.java)

                        Glide.with(context).load(chatPartnerUser2!!.ImageUrl).into(viewHolder.itemView.recent_chat_User_image)

                        viewHolder.itemView.recent_chats_username.text = chatPartnerUser2!!.username

                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")

            }

        })

    }

    override fun getLayout(): Int {
        return R.layout.chats_latest_single_row
    }

}
