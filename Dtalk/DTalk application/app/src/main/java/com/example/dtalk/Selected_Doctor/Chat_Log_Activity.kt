package com.example.dtalk.Selected_Doctor

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.dtalk.Model_Class.ChatlogUsersList
import com.example.dtalk.Model_Class.Doctors_
import com.example.dtalk.Model_Class.appUsers
import com.example.dtalk.R
import com.example.dtalk.View_Pager.After_Doctor_Selected_Activity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.activity_chat_log.*
import kotlinx.android.synthetic.main.chat_from_me.view.*
import kotlinx.android.synthetic.main.chat_to_other.view.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Chat_Log_Activity : AppCompatActivity() {

    lateinit var mUser: FirebaseUser
    lateinit var mAuth: FirebaseAuth
    lateinit var Userref: DatabaseReference

    var otherId: String?=null

    val madapter = GroupAdapter<GroupieViewHolder>()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_log)


        val chatDoc = intent.getParcelableExtra<Doctors_>(After_Doctor_Selected_Activity.chatKey)

        chatsRecycler.adapter = madapter
        chatsRecycler.setHasFixedSize(true)
        chatsRecycler.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)


        mAuth = FirebaseAuth.getInstance()
        mUser = mAuth.currentUser!!


        if(chatDoc!=null){

            //setting Dr. image on top
            Glide.with(this).load(chatDoc.ImageUrl).into(Doc_Profile_photo)


            //setting my image at bottom
            val myRef = FirebaseDatabase.getInstance().getReference().child("Doctors").child(mUser.uid)
            myRef.addValueEventListener(object :ValueEventListener{


                override fun onDataChange(snapshot: DataSnapshot) {

                    if (snapshot.exists()){

                        val userProfileImg = snapshot.child("ImageUrl").value.toString()

                        Glide.with(this@Chat_Log_Activity).load(userProfileImg).placeholder(R.drawable.user_default_pic).into(MyImage)


                    }


                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }


            })


            //setting Dr. name on top
            Chatting_Doc_Name.text = chatDoc.username


            //other id and myId for sending messages
            otherId = chatDoc.uid

            val myid = mUser.uid

        }

        displayMessage()

        Send_button.setOnClickListener{

            performSendMessage()
        }

    }

    private fun displayMessage() {


        val ref1 = FirebaseDatabase.getInstance().getReference("/User Messages/${mUser.uid}/${otherId}")

        ref1.addChildEventListener(object: ChildEventListener{

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {

                if (p0.exists()) {

                    val chatMessage = p0.getValue(ChatlogUsersList::class.java)
                    if (chatMessage!=null){

                        if (chatMessage.SenderId == FirebaseAuth.getInstance().uid) {

                            madapter.add(ChatFromItem(chatMessage.Chats!!, chatMessage.Date!!))
                        }

                        else {

                            madapter.add(ChatToItem(chatMessage.Chats!!,chatMessage.Date!!))

                        }

                    }

                }

            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                TODO("Not yet implemented")
            }

            override fun onChildRemoved(p0: DataSnapshot) {
                TODO("Not yet implemented")
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }

        })


    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun performSendMessage() {

        // how do we actually send a message to firebase...
        val Chats = Message_bar.text.toString()

        if (Chats.isEmpty()){

            return    //returning so that no to send empty message
        }

        else{

            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("h:mm a")   //d MMM yyyy, HH:mm:ss
            val formatted = current.format(formatter)


            val reference = FirebaseDatabase.getInstance().getReference("/User Messages/${mUser.uid}/${otherId}").push()

            val toReference = FirebaseDatabase.getInstance().getReference("/User Messages/${otherId}/${mUser.uid}").push()

            val chatMessage = ChatlogUsersList(Chats,formatted, otherId!!,mUser.uid)


            reference.setValue(chatMessage)
                .addOnSuccessListener {
                    Log.d("ChatLog", "Saved our chat message: ${reference.key}")

                    Message_bar.text.clear()

                    chatsRecycler.scrollToPosition(madapter.itemCount - 1)
                }

            toReference.setValue(chatMessage)

                .addOnSuccessListener {

                    chatsRecycler.scrollToPosition(madapter.itemCount - 1)

                }


            //For showing these messages in chats as recent chats
            val RecentchatRefme = FirebaseDatabase.getInstance().getReference("/Recent Chats/${mUser.uid}/${otherId}")
            RecentchatRefme.setValue(chatMessage)

            val RecentchatRefTo = FirebaseDatabase.getInstance().getReference("/Recent Chats/${otherId}/${mUser.uid}")
            RecentchatRefTo.setValue(chatMessage)

        }//else loop


    }

}

class ChatFromItem(val text: String,val time: String): Item<GroupieViewHolder>() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {

        viewHolder.itemView.textview_from_row.text = text

        viewHolder.itemView.time_from.text = time

    }

    override fun getLayout(): Int {
        return R.layout.chat_from_me
    }

}

class ChatToItem(val text: String, val time: String): Item<GroupieViewHolder>() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.textview_to_row.text = text
        viewHolder.itemView.time_to.text = time

    }

    override fun getLayout(): Int {
        return R.layout.chat_to_other
    }

}