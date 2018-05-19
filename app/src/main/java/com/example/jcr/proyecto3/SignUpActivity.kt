package com.example.jcr.proyecto3

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity() {

    private lateinit var txtName:EditText
    private lateinit var txtLastName:EditText
    private lateinit var editEmail:EditText
    private lateinit var editPassword:EditText
    private lateinit var progressBar:ProgressBar
    private lateinit var dbReference: DatabaseReference
    private lateinit var database:FirebaseDatabase
    private lateinit var auth:FirebaseAuth
    private lateinit var btnSignUp:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        txtName = findViewById(R.id.txtName)
        txtLastName = findViewById(R.id.txtLastName)

        editEmail = findViewById(R.id.editEmail)
        editPassword = findViewById(R.id.editPassword)

        btnSignUp = findViewById(R.id.btnSignUp)

        progressBar = findViewById(R.id.progressBar)

        database = FirebaseDatabase.getInstance()

        auth = FirebaseAuth.getInstance()
        dbReference = database.reference.child("User")

        btnSignUp.setOnClickListener(View.OnClickListener {
            createNewAccount()
        })

    }



    private fun createNewAccount(){
        val name:String = txtName.text.toString()
        val lastName:String = txtLastName.text.toString()
        val email:String = editEmail.text.toString()
        val password:String = editPassword.text.toString()

        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(lastName) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){
            progressBar.visibility = View.VISIBLE

            auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this){
                    task ->
                        if (task.isComplete) {
                            val user: FirebaseUser? = auth.currentUser
                            verifyEmail(user)
                            val userDB = dbReference.child(user?.uid)
                                userDB.child("Name").setValue(name)
                                userDB.child("LastName").setValue(lastName)
                                action()
                        }
            }
        }
    }

    /*
    * Accion que se realiza cuando t/odo se cumple correctamente
    */

    private fun action(){
        val intent = Intent(this, LoginActivity::class.java)
        finish()
        startActivity(intent)
    }

    private fun verifyEmail(user:FirebaseUser?){
        user?.sendEmailVerification()
                ?.addOnCompleteListener(this) {
                    task ->
                        if(task.isComplete){
                            Toast.makeText(this,"Email enviado",Toast.LENGTH_SHORT).show()
                        }
                        else{
                            Toast.makeText(this,"Error al enviar",Toast.LENGTH_SHORT).show()
                        }
                }
    }

}
