package com.soban.perfectpataa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.soban.perfectpataa.databinding.ActivityConsumerFullDetailsBinding

class EditUserFullDetails : AppCompatActivity() {
    private lateinit var binding: ActivityConsumerFullDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConsumerFullDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Consumer Details"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val consumerId = intent.getStringExtra("consumerId")
        val consumerNumber = intent.getStringExtra("consumerNumber")

        binding.progressLayout.visibility = View.VISIBLE
        val ref = FirebaseDatabase.getInstance().getReference("/userDetails").child(consumerId!!)
        ref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()){
                    binding.progressLayout.visibility = View.GONE
                    binding.llMainLayoutWithAllContents.visibility = View.VISIBLE

                    val userDataFromFirebase: UserFullDetailsModel = snapshot.getValue(UserFullDetailsModel::class.java)!!

                    binding.txtConsumerName.text = userDataFromFirebase.consumerName
                    binding.txtConsumerAddress.text = userDataFromFirebase.address
                    binding.txtConsumerLandmark.text = userDataFromFirebase.landMark
                    binding.txtConsumerCity.text = userDataFromFirebase.cityName
                    binding.txtConsumerType.text = userDataFromFirebase.consumerType
                    binding.txtConsumerMobileNum.text = userDataFromFirebase.mobileNumber.toString()

                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

        //setting the values of consumerId, consumerNumber variables(which are fetched from intent) to the text views
        binding.txtConsumerid.text = consumerId
        binding.txtConsumerNum.text = consumerNumber
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.edituserdeatils_deliveryman,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when(item.itemId){
            R.id.editdetails -> startActivity(Intent(this,EditConsumerDetails::class.java))
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}

data class UserFullDetailsModel(
    var consumerName: String = "",
    var mobileNumber: Long = 0,
    var address: String = "",
    var landMark: String = "",
    var cityName: String = "",
    var consumerType: String = ""
)