package com.soban.perfectpataa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.soban.perfectpataa.databinding.ActivityDeliveryManDashboardBinding

class DeliveryManDashboard : AppCompatActivity() {

    private lateinit var binding: ActivityDeliveryManDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeliveryManDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSignout.setOnClickListener {
            Firebase.auth.signOut()
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
        binding.btnSearchconsumer.setOnClickListener {
            startActivity(Intent(this,DeliveryManSearchUser::class.java))
        }

    }
}