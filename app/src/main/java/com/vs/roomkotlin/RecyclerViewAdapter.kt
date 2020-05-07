package com.vs.roomkotlin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vs.roomkotlin.room.RoomEntity
import java.util.*

class RecyclerViewAdapter :
    RecyclerView.Adapter<RecyclerViewAdapter.NameHolder>() {
    private var names: List<RoomEntity>? = ArrayList()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NameHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_item, parent, false)
        return NameHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: NameHolder,
        position: Int
    ) {
        val currentNote = names!![position]
        holder.textViewTitle.text = currentNote.firstName
        holder.textViewDescription.text = currentNote.lastName
    }

    override fun getItemCount(): Int {
        return names!!.size
    }

    fun setNames(names: List<RoomEntity>?) {
        this.names = names
        notifyDataSetChanged()
    }

    fun getNoteAt(position: Int): RoomEntity {
        return names!![position]
    }

    class NameHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val textViewTitle: TextView = itemView.findViewById(R.id.textView)
        val textViewDescription: TextView = itemView.findViewById(R.id.textView1)

        init {
            itemView.setOnClickListener {

            }
        }
    }
}