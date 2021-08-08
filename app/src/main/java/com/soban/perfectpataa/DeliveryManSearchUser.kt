package com.soban.perfectpataa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.soban.perfectpataa.databinding.ActivityDeliveryManSearchUserBinding

class DeliveryManSearchUser : AppCompatActivity() {

    //............global variables
        //...............for binding views
    private lateinit var binding: ActivityDeliveryManSearchUserBinding

        //..............array lists for storing database data
    var usersLIst: ArrayList<DeliveryManUserSearchModel> = ArrayList()
    var usersList_filtered: ArrayList<DeliveryManUserSearchModel> = ArrayList()

        //..............adapter
    var deliveryManUserSearchAdapter = usersLIst.let { DeliveryManUserSearchAdapter(it) }

        //..............store the intent came from previous activity
    lateinit var consumeridOrNumber : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeliveryManSearchUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //shiding the action bar because of search activity
        supportActionBar?.hide()

        binding.recyclerviewUserslist.hasFixedSize()
        binding.recyclerviewUserslist.layoutManager = LinearLayoutManager(this)

        //...............initializing the arrayLists
        usersLIst = arrayListOf<DeliveryManUserSearchModel>()
        usersList_filtered = arrayListOf<DeliveryManUserSearchModel>()

        //..............fetching intent data from previous activity
        consumeridOrNumber = "Consumer Id"
        deliveryManUserSearchAdapter.idorNum = "Consumer Id"

        //..............making progress layout visible
        binding.progressLayout.visibility = View.VISIBLE

        binding.imgChangetypeOfSearch.setOnClickListener {
            val mDialogView = LayoutInflater.from(this).inflate(R.layout.dialog_howtosearchirornum, null)
            val mBuilder = AlertDialog.Builder(this).setView(mDialogView)
            val mAlertDialog = mBuilder.show()

            val radioGroup = mDialogView.findViewById<RadioGroup>(R.id.radioGroup)
            val radio_consid = mDialogView.findViewById<RadioButton>(R.id.radio_consid)
            val radio_consnum = mDialogView.findViewById<RadioButton>(R.id.radio_consnum)
            val txt_submitradiodialog = mDialogView.findViewById<TextView>(R.id.txt_submitradiodialog)

            if (consumeridOrNumber.equals("Consumer Id")){
                radio_consid.isChecked = true
                radio_consnum.isChecked = false
            }else if (consumeridOrNumber.equals("Consumer Number")){
                radio_consnum.isChecked = true
                radio_consid.isChecked = false
            }

            txt_submitradiodialog.setOnClickListener {
                val btnId = radioGroup.checkedRadioButtonId
                val radioButton = mDialogView.findViewById<RadioButton>(btnId)

                consumeridOrNumber = radioButton.text.toString()
                deliveryManUserSearchAdapter.idorNum = radioButton.text.toString()

                mAlertDialog.dismiss()
            }
        }

        //.................fetching data from firebase realtime database
        val ref = FirebaseDatabase.getInstance().getReference("/searchUser")
        ref.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                //after data fetched...making progress layout invisible and main search and recyclerview VISIBLE
                binding.progressLayout.visibility = View.GONE
                binding.llMainLayoutWithSearchViewRecyclerView.visibility = View.VISIBLE

                //...............loop for adding data to arrayList userslist
                snapshot.children.forEach {
                    val data : DeliveryManUserSearchModel = it.getValue(DeliveryManUserSearchModel::class.java)!!
                    usersLIst.add(data)
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

        //.............code for listening if user entered any values in search editText
        binding.edttxtSearchuser.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { /*..do nothing..*/ }

            //............if text changes
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                //...............calling the function and passing parameter to function
                val searchText: String = binding.edttxtSearchuser.text.toString()
                searchInRealtimeDatabase(searchText.lowercase())
            }

            override fun afterTextChanged(p0: Editable?) { /*..do nothing..*/ }

        })
    }

    private fun searchInRealtimeDatabase(searchText: String) {

        //...........clearing the arrayList
        usersList_filtered.clear()

        //looping through the array list userList to check whether it contains the value which user want to search
        usersLIst.forEach{
            //............if availble then adding it to arrayList usersList_filtered

            if (consumeridOrNumber.equals("Consumer Id")){
                if (it.consumerId.lowercase().contains(searchText)){
                    usersList_filtered.add(it)
                }
            }else if (consumeridOrNumber.equals("Consumer Number")){
                if (it.consumerNumber.lowercase().contains(searchText)){
                    usersList_filtered.add(it)
                }
            }
        }

        //................passing new arrayList to Adapter
        deliveryManUserSearchAdapter.userList = usersList_filtered
        deliveryManUserSearchAdapter.notifyDataSetChanged()
        binding.recyclerviewUserslist.adapter = deliveryManUserSearchAdapter

    }
}