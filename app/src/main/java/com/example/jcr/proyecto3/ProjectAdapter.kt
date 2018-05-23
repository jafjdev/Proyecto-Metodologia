package com.example.jcr.proyecto3

import android.content.Context
import android.support.design.widget.Snackbar
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import android.widget.TextView


class ProjectAdapter(var list: ArrayList<Project>) : RecyclerView.Adapter<ProjectAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.content_item, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ProjectAdapter.ViewHolder, position: Int) {
        holder.bindItem(list[position])
    }

    //clase anidada donde se maneja la vista.
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindItem(data: Project) {
            val thumbnail: AppCompatImageView = itemView.findViewById(R.id.thumbnail)
            val title: AppCompatTextView = itemView.findViewById(R.id.txtTitle)
            val description: AppCompatTextView = itemView.findViewById(R.id.txtDescription)

            title.text = data.name
            description.text = data.description
            //Glide.with(itemView.context).load(data.uidCreador).into(thumbnail)
            itemView.setOnClickListener {
                Snackbar.make(itemView, "Projecto name: ${data.name}", Snackbar.LENGTH_LONG).show();
            }
        }
    }
}