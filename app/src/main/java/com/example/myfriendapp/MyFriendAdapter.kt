package com.example.myfriendapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyFriendAdapter (private val context: Context, private val items: ArrayList<Myfriend>):
RecyclerView.Adapter<MyFriendAdapter.ViewHolder>(){
    

    class ViewHolder (itemview: View):RecyclerView.ViewHolder(itemview){
        private var txtFriendname: TextView = itemview.findViewById(R.id.txFriendName)
        private var txtFriendEmail: TextView = itemview.findViewById(R.id.txFriendEmail)
        private var txtFriendPhone: TextView = itemview.findViewById(R.id.txFriendTelp)

        fun bindItem(item: Myfriend){
            txtFriendname.text = item.nama
            txtFriendEmail.text = item.email
            txtFriendPhone.text = item.telp
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, ViewType: Int)= ViewHolder(LayoutInflater
        .from(context).inflate(R.layout.my_friend_item,parent,false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(items.get(position))
    }

    override fun getItemCount(): Int = items.size
        
    }
