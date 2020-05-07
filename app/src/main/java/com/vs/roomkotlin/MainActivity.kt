package com.vs.roomkotlin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.vs.roomkotlin.room.RoomEntity
import com.vs.roomkotlin.room.RoomViewModel

class MainActivity : AppCompatActivity() {
    private var nameViewModel: RoomViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val buttonAddNote = findViewById<FloatingActionButton>(R.id.fab)
        buttonAddNote.setOnClickListener {
            val intent = Intent(this@MainActivity, AddName::class.java)
            startActivityForResult(intent, ADD_NAME_REQUEST)
        }
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        val adapter = RecyclerViewAdapter()
        recyclerView.adapter = adapter
        nameViewModel = ViewModelProvider(this).get(RoomViewModel::class.java)
        nameViewModel!!.allNotes?.observe(
            this,
            Observer<List<RoomEntity?>?> { notes -> adapter.setNames(notes as List<RoomEntity>?) })
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(
                viewHolder: RecyclerView.ViewHolder,
                direction: Int
            ) {
                nameViewModel!!.delete(adapter.getNoteAt(viewHolder.adapterPosition))
                Toast.makeText(this@MainActivity, "Note deleted", Toast.LENGTH_SHORT).show()
            }
        }).attachToRecyclerView(recyclerView)
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data!!)
        if (requestCode == ADD_NAME_REQUEST && resultCode == Activity.RESULT_OK) {
            val title = data.getStringExtra(AddName.EXTRA_FIRST_NAME)
            val description = data.getStringExtra(AddName.EXTRA_LAST_NAME)
            val note = RoomEntity(title!!, description!!)
            nameViewModel?.insert(note)
            Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Note not saved", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.delete_all_notes) {
            nameViewModel?.deleteAllNotes()
            Toast.makeText(this, "All notes deleted", Toast.LENGTH_SHORT).show()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val ADD_NAME_REQUEST = 1
    }
}