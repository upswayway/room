package com.example.roomdatabase

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    private var list = mutableListOf<User>()
    private var actionEdit : ((User) -> Unit)? = null
    private var actionDelete : ((User) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.raw_item_recyclerview, parent, false)

        return UserViewHolder(view)
    }

    override fun getItemCount() = list.size

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<User>){
        list.apply {
            clear()
            addAll(data)
        }
        notifyDataSetChanged()
    }

    fun setOnActionEditListener(callback: (User) -> Unit) {
        this.actionEdit = callback
    }

    fun setOnActionDeleteListener(callback: (User) -> Unit){
        this.actionDelete = callback
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = list[position]
        holder.name.text = user.Name
        holder.designation.text = user.Designation
        holder.company.text = user.Company

        holder.actionEdit.setOnClickListener{ actionEdit?.invoke(user)}
        holder.actionDelete.setOnClickListener{ actionDelete?.invoke(user)}
    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name : TextView = itemView.findViewById(R.id.user_Name)
        val designation : TextView = itemView.findViewById(R.id.user_Designation)
        val company : TextView = itemView.findViewById(R.id.user_Company)

        val actionEdit : ImageView = itemView.findViewById(R.id.action_update)
        val actionDelete : ImageView = itemView.findViewById(R.id.action_delete)
    }
}