package com.example.jcr.proyecto3

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DatabaseReference
import kotlinx.android.synthetic.main.activity_new_project.*
import kotlin.collections.ArrayList


class NewProjectActivity : AppCompatActivity() {
    private var agregarUsuBtn: Button? = null
    private var crearpBtn:Button?  = null
    private var editNombre:EditText? = null
    private var editDescripcion:EditText? = null
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    private lateinit var userList: ArrayList<User>
    private lateinit var mDatabase: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_project)

        agregarUsuBtn = findViewById<View>(R.id.agregarUsuariosBtn) as Button
        crearpBtn = findViewById<View>(R.id.crearProyectoBtn) as Button

        editNombre = findViewById<View>(R.id.editNombreP) as EditText
        editDescripcion = findViewById<View>(R.id.editDesc) as EditText

        auth = FirebaseAuth.getInstance()

        mDatabase = FirebaseDatabase.getInstance().getReference()


        agregarUsuBtn?.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        })

        crearpBtn?.setOnClickListener(View.OnClickListener {
            val nombreP = editNombreP.text.toString()
            val descP = editDescripcion!!.text.toString()
            createNewProject(nombreP,descP)
        })


    }

    private fun createNewProject(name: String, description: String) {
        val project:Project = Project(name, description,auth.uid)
        mDatabase.child("Project").child(project!!.name).setValue(project)
    }
}
