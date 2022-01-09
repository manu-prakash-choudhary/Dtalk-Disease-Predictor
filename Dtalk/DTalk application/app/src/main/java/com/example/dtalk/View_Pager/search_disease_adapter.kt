package com.example.dtalk.View_Pager

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.RecyclerView
import com.example.dtalk.Model_Class.symptoms_nameList
import com.example.dtalk.R

open class search_disease_adapterclass (val symList: ArrayList<symptoms_nameList>, val context: Context) : RecyclerView.Adapter<search_disease_adapterclass.dataHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): dataHolder {
        val myView = LayoutInflater.from(parent.context).inflate(R.layout.symptom_recyler_item,parent,false)

        return dataHolder(myView)
    }

    override fun getItemCount(): Int {
        return symList.size
    }

    override fun onBindViewHolder(holder: dataHolder, position: Int) {
        val sym_name: symptoms_nameList = symList[position]

        holder.text1.text = sym_name.sympName

    }


    inner class dataHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val text1 = itemView.findViewById(R.id.item) as TextView


    }



}

