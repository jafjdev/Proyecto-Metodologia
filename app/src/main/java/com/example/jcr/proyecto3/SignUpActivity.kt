package com.example.jcr.proyecto3

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.regex.Pattern
import android.support.annotation.NonNull



class SignUpActivity : AppCompatActivity() {

    private lateinit var txtName: EditText
    private lateinit var txtLastName: EditText
    private lateinit var editEmail: EditText
    private lateinit var editPassword: EditText
    private lateinit var progressBar: ProgressBar
    private lateinit var dbReference: DatabaseReference
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    private lateinit var btnSignUp: Button


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
        dbReference = FirebaseDatabase.getInstance().reference.child("User")

        btnSignUp.setOnClickListener(View.OnClickListener {
            createNewAccount()
        })
    }

    private fun createNewAccount() {
        val user: FirebaseUser? = auth.currentUser
        val name: String = txtName.text.toString()
        val lastName: String = txtLastName.text.toString()
        val email: String = editEmail.text.toString()
        val password: String = editPassword.text.toString()

        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(lastName) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && isValidEmail(email)) {
            progressBar.visibility = View.VISIBLE

            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
                if (task.isComplete) {
                    verifyEmail(user)
                    val userDB = dbReference.child(user!!.uid)
                    userDB.child("name").setValue(name)
                    userDB.child("lastName").setValue(lastName)
                    userDB.child("uuid").setValue(auth.uid)
                    action()
                } else {
                    Toast.makeText(this,
                            "Failed to send verification email.",
                            Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    /**Accion que se realiza al verificar que tdo lo anterior se realice correctamente
     * @author: josejecr97
     * */

    private fun action() {
        val intent = Intent(this, LoginActivity::class.java)
        auth.signOut()
        finish()
        startActivity(intent)
    }

    /**Funcion para verificar que el correo electronico este escrito en el formato correcto
     * @author: josejecr97
     * @return True en caso de que el correo cumpla el formato adecuado / false en caso de que no
     * */
    fun isValidEmail(target: CharSequence): Boolean {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }


    /**Metodo que envia un correo electronico al usuario para que verifique el correo electronico
     * @author: josejecr97
     * */
    private fun verifyEmail(user: FirebaseUser?) {
        user?.sendEmailVerification()
                ?.addOnCompleteListener(this) { task ->
                    if (task.isComplete) {
                        Toast.makeText(this, "Email enviado", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Error al enviar", Toast.LENGTH_SHORT).show()
                    }
                }
    }

    /**Procedimiento que se ejecuta al precionar la tecla de back,
     * el usuario sera llevado a la ventana de login y se realizara
     * la desconeccion de la base de datos
     * @author: josejecr97
     * */
    override fun onBackPressed() {
        val intent = Intent(this, LoginActivity::class.java)
        auth.signOut()
        finish()
        startActivity(intent)
    }
}
