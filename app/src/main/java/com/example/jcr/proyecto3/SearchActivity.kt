package com.example.jcr.proyecto3

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.bumptech.glide.Glide
import com.firebase.ui.database.FirebaseRecyclerAdapter
import kotlinx.android.synthetic.main.list_layout.view.*


class SearchActivity : AppCompatActivity() {
    lateinit var mSearchText: EditText
    lateinit var mRecyclerView: RecyclerView
    lateinit var mDatabase: DatabaseReference
    lateinit var FirebaseRecyclerAdapter: FirebaseRecyclerAdapter<User, UsersViewHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        mSearchText = findViewById(R.id.search_field)
        mRecyclerView = findViewById(R.id.result_list)

        mDatabase = FirebaseDatabase.getInstance().getReference("User")
        mRecyclerView.setHasFixedSize(true)

        mRecyclerView.setLayoutManager(LinearLayoutManager(this))

        mSearchText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                val searchText = mSearchText.getText().toString().trim()

                loadFirebaseData(searchText)
            }
        })

    }


    private fun loadFirebaseData(searchText: String) {

        if (searchText.isEmpty()) {

            FirebaseRecyclerAdapter.cleanup()
            mRecyclerView.adapter = FirebaseRecyclerAdapter

        } else {
            val firebaseSearchQuery = mDatabase.orderByChild("name").startAt(searchText).endAt(searchText + "\uf8ff")

            FirebaseRecyclerAdapter = object : FirebaseRecyclerAdapter<User, UsersViewHolder>(
                    User::class.java,
                    R.layout.list_layout,
                    UsersViewHolder::class.java,
                    firebaseSearchQuery
            ) {
                override fun populateViewHolder(viewHolder: UsersViewHolder, model: User?, position: Int) {

                    viewHolder.mview.userName.setText(model?.name)
                    viewHolder.mview.status_text.setText(model?.lastName)
                    //Picasso.with(applicationContext).load(model?.image).into(viewHolder.mview.UserImageView)
                }
            }
            mRecyclerView.adapter = FirebaseRecyclerAdapter
        }
    }

    class UsersViewHolder(var mview: View) : RecyclerView.ViewHolder(mview) {}
}
