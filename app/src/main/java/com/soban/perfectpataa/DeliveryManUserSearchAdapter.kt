package com.soban.perfectpataa

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DeliveryManUserSearchAdapter(var userList: ArrayList<DeliveryManUserSearchModel>) : RecyclerView.Adapter<DeliveryManUserSearchAdapter.UsersListViewHolder>(){

    //taking the value of this variable from activity to identify whether user searches id or number
    lateinit var idorNum: String

    class UsersListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val ll_singleitemIdorNum = itemView.findViewById<LinearLayout>(R.id.ll_singleitemIdorNum)
        val txt_consumerid = itemView.findViewById<TextView>(R.id.txt_consumerid)
        val txt_consumerNum = itemView.findViewById<TextView>(R.id.txt_consumerNum)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.singleitem_deliverymanusersearch,parent,false)
        return UsersListViewHolder(view)
    }

    override fun onBindViewHolder(holder: UsersListViewHolder, position: Int) {
        val dataFromFirebase: DeliveryManUserSearchModel = userList[position]

        holder.txt_consumerid.text = dataFromFirebase.consumerId
        holder.txt_consumerNum.text = dataFromFirebase.consumerNumber

        holder.ll_singleitemIdorNum.setOnClickListener {
            val intent = Intent(it.context,EditUserFullDetails::class.java)
            intent.putExtra("consumerId",dataFromFirebase.consumerId)
            intent.putExtra("consumerNumber",dataFromFirebase.consumerNumber)
            it.context.startActivity(intent)
        }

        if (idorNum.equals("Consumer Id")){
            holder.txt_consumerid.visibility = View.VISIBLE
            holder.txt_consumerNum.visibility = View.GONE
        }else if (idorNum.equals("Consumer Number")){
            holder.txt_consumerNum.visibility = View.VISIBLE
            holder.txt_consumerid.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

}