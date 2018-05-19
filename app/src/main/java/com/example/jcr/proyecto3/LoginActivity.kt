package com.example.jcr.proyecto3

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.TextView

class LoginActivity : AppCompatActivity() {


    // UI references.
    private var mEmailView: TextInputEditText? = null
    private var mPasswordView: TextInputEditText? = null
    private var mLoginFormView: View? = null
    private var email_sign_in_button:Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mEmailView = findViewById<View>(R.id.email) as TextInputEditText
        email_sign_in_button = findViewById<View>(R.id.email_sign_in_button) as Button

        mPasswordView = findViewById<View>(R.id.password) as TextInputEditText
        mPasswordView!!.setOnEditorActionListener(TextView.OnEditorActionListener { textView, id, keyEvent ->
            if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                //attemptLogin()
                return@OnEditorActionListener true }
            false
        })

        //btn
        email_sign_in_button!!.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                val intent = Intent(v!!.context, MainActivity::class.java)
                startActivity(intent)
            }
        })

        //val mEmailSignInButton = findViewById<View>(R.id.email_sign_in_button) as Button
        mLoginFormView = findViewById(R.id.login_form)
    }
}

