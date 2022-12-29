package com.vs.roomkotlin

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.vs.roomkotlin.RecyclerViewAdapter.NameHolder
import com.vs.roomkotlin.room.MyRoomEntity
import java.util.*

class RecyclerViewAdapter :
    RecyclerView.Adapter<NameHolder>() {
    private var names: List<MyRoomEntity>? = ArrayList()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NameHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_item, parent, false)
        return NameHolder(itemView, parent.context)
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

    fun setNames(names: List<MyRoomEntity>?) {
        this.names = names
        notifyItemChanged(0)
    }

    fun getNoteAt(position: Int): MyRoomEntity {
        return names!![position]
    }

    class NameHolder(itemView: View, context: Context) :
        RecyclerView.ViewHolder(itemView) {
        val textViewTitle: TextView = itemView.findViewById(R.id.textView)
        val textViewDescription: TextView = itemView.findViewById(R.id.textView1)

        init {
            itemView.setOnClickListener {
                Toast.makeText(
                    context,
                    textViewTitle.text.toString() + " " + textViewDescription.text.toString(),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}