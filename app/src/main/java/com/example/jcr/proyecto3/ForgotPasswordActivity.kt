package com.example.jcr.proyecto3

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var editEmail: TextView
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        editEmail = findViewById(R.id.txtEmailfrgt)
        auth = FirebaseAuth.getInstance()
    }

    fun send(view:View){
        val email = editEmail.text.toString()
            if(!TextUtils.isEmpty(email))
                auth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(this){
                            task->
                                if(task.isSuccessful){
                                    val intent = Intent(this, LoginActivity::class.java)
                                    finish()
                                    startActivity(intent)
                                }
                                else
                                    Toast.makeText(this,"Error  al enviar email",Toast.LENGTH_SHORT)
                        }

    }
}
