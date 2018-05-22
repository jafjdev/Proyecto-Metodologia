package com.example.jcr.proyecto3

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DatabaseReference
import kotlin.collections.ArrayList


class NewProjectActivity : AppCompatActivity() {
    private var agregarUsuBtn: Button? = null
    private lateinit var dbReference: DatabaseReference
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    private lateinit var userList: ArrayList<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_project)
        agregarUsuBtn = findViewById<View>(R.id.agregarUsuariosBtn) as Button
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()


        agregarUsuBtn?.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        })

    }
}
