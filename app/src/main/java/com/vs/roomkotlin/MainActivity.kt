package com.vs.roomkotlin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.vs.roomkotlin.room.MyRoomEntity

class MainActivity : AppCompatActivity() {
    private var nameViewModel: MainViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val buttonAddNote = findViewById<FloatingActionButton>(R.id.fab)
        buttonAddNote.setOnClickListener {
            val intent = Intent(this@MainActivity, AddName::class.java)
            activityForResult.launch(intent)
        }
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        val adapter = RecyclerViewAdapter()
        recyclerView.adapter = adapter
        nameViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        nameViewModel!!.allName?.observe(
            this,
            { names -> adapter.setNames(names) }
        )
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

    private var activityForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val title = data?.getStringExtra(AddName.EXTRA_FIRST_NAME)
                val description = data?.getStringExtra(AddName.EXTRA_LAST_NAME)
                val note = MyRoomEntity(title!!, description!!)
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
        if (item.itemId == R.id.delete_all_names) {
            nameViewModel?.deleteAllNames()
            Toast.makeText(this, "All names deleted", Toast.LENGTH_SHORT).show()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}