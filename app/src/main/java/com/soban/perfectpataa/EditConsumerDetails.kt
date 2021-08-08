package com.soban.perfectpataa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.soban.perfectpataa.databinding.ActivityEditConsumerDetailsBinding

class EditConsumerDetails : AppCompatActivity() {
    private lateinit var binding: ActivityEditConsumerDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditConsumerDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Edit Details"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        binding.imgEditName.setOnClickListener {

        }
        binding.imgEditAddress.setOnClickListener {

        }
        binding.imgEditLandMark.setOnClickListener {

        }
        binding.imgEditCity.setOnClickListener {

        }
        binding.imgEditMobileNumber.setOnClickListener {

        }
        binding.imgEditType.setOnClickListener {

        }

    }



    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}