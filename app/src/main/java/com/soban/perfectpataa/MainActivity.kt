package com.soban.perfectpataa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.soban.perfectpataa.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    var otpid: String = ""

    val auth = FirebaseAuth.getInstance()

    lateinit var phoneNumber : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.btnGetotp.setOnClickListener {
            if (binding.edttxtEntermobilenum.text.toString().isEmpty()){
                Toast.makeText(this@MainActivity,"Please enter mobile number",Toast.LENGTH_SHORT).show()
            }else if (binding.edttxtEntermobilenum.text.toString().length < 10){
                Toast.makeText(this@MainActivity,"Please enter valid mobile number",Toast.LENGTH_SHORT).show()
            }else{
                binding.llLoginnumberlayout.visibility = View.GONE
                binding.llOtplayout.visibility = View.VISIBLE

                phoneNumber = "+91${binding.edttxtEntermobilenum.text}"

                binding.txtEnterotpsenton.text = "Enter OTP sent on $phoneNumber"

                generateOTP()
            }
        }

        binding.btnProceedotp.setOnClickListener {
            if (binding.edttxtEnterotp.text.toString().isEmpty()){
                Toast.makeText(this@MainActivity,"Please enter OTP",Toast.LENGTH_SHORT).show()
            }else if (binding.edttxtEnterotp.text.toString().length < 6){
                Toast.makeText(this@MainActivity,"Enter a valid OTP",Toast.LENGTH_SHORT).show()
            }else{
                val credential: PhoneAuthCredential = PhoneAuthProvider.getCredential(otpid,binding.edttxtEnterotp.text.toString())
                signInWithPhoneAuthCredential(credential)
            }
        }

        binding.txtRetryforotp.setOnClickListener {
            binding.llLoginnumberlayout.visibility = View.VISIBLE
            binding.llOtplayout.visibility = View.GONE
        }
    }

    private fun generateOTP() {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            phoneNumber,
            60,
            TimeUnit.SECONDS,
            this,
            object : PhoneAuthProvider.OnVerificationStateChangedCallbacks(){

                override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                    otpid = p0
                }

                override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                    signInWithPhoneAuthCredential(p0)
                }

                override fun onVerificationFailed(p0: FirebaseException) {
                    Toast.makeText(this@MainActivity,p0.message,Toast.LENGTH_LONG).show()
                }

            }
        )
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    startActivity(Intent(this,DeliveryManDashboard::class.java))
                    finish()
                } else {
                    Toast.makeText(this,"signin error",Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun onStart() {
        if (auth.currentUser != null){
            startActivity(Intent(this,DeliveryManDashboard::class.java))
            finish()
        }
        super.onStart()
    }
}