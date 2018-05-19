package com.example.jcr.proyecto3

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatImageView
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {


    // UI references.

    private var mEmailView: TextInputEditText? = null
    private var mPasswordView: TextInputEditText? = null

    private lateinit var imagen:AppCompatImageView

    private var mLoginFormView: View? = null
    private var email_sign_in_button: Button? = null
    private var txtSignUp: TextView? = null

    private var txtFrgtPassword: TextView? = null

    private lateinit var progressBar:ProgressBar
    private lateinit var auth:FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        mEmailView = findViewById<View>(R.id.email) as TextInputEditText
        mPasswordView = findViewById<View>(R.id.password) as TextInputEditText

        imagen = findViewById<View>(R.id.imagenLogin) as AppCompatImageView
        email_sign_in_button = findViewById<View>(R.id.email_sign_in_button) as Button
        txtSignUp = findViewById<View>(R.id.txtSignUp) as TextView

        progressBar = findViewById<View>(R.id.progressBarLogin) as ProgressBar
        txtFrgtPassword = findViewById<View>(R.id.txtFrgtPassword) as TextView


        txtFrgtPassword!!.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                forgotPaction()
            }
        })

        mPasswordView!!.setOnEditorActionListener(TextView.OnEditorActionListener { textView, id, keyEvent ->
            if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                //attemptLogin()
                return@OnEditorActionListener true
            }
            false
        })

        //signUp
        txtSignUp!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val intent = Intent(v!!.context, SignUpActivity::class.java)
                finish()
                startActivity(intent)
            }
        })


        //btn
        email_sign_in_button!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                loginUser()
            }
        })

        //val mEmailSignInButton = findViewById<View>(R.id.email_sign_in_button) as Button
        mLoginFormView = findViewById(R.id.login_form)
    }

    private fun loginUser(){
        val user:String=mEmailView?.text.toString()
        val password:String=mPasswordView?.text.toString()
        if (!TextUtils.isEmpty(user) && !TextUtils.isEmpty(password)){
            imagen.visibility = View.INVISIBLE
            progressBar.visibility = View.VISIBLE
            auth.signInWithEmailAndPassword(user,password)
                    .addOnCompleteListener(this){
                        task->
                            if(task.isSuccessful)
                                action()
                            else{
                                imagen.visibility = View.VISIBLE
                                progressBar.visibility = View.INVISIBLE
                            }
                    }
            }
    }

    private fun action(){
        val intent = Intent(this, MainActivity::class.java)
        finish()
        startActivity(intent)
    }

    private fun forgotPaction(){
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            finish()
            startActivity(intent)
    }

}

