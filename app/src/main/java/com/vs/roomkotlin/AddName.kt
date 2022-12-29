package com.vs.roomkotlin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class AddName : AppCompatActivity() {
    private var editTextTitle: EditText? = null
    private var editTextDescription: EditText? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_name)
        editTextTitle = findViewById(R.id.first_name)
        editTextDescription = findViewById(R.id.last_name)
        val fab =
            findViewById<FloatingActionButton>(R.id.floatingActionButton)
        fab.setOnClickListener { saveName() }
        title = "Add Name"
    }

    private fun saveName() {
        val title = editTextTitle!!.text.toString()
        val description = editTextDescription!!.text.toString()
        if (title.trim().isEmpty() || description.trim().isEmpty()) {
            Toast.makeText(this, "Please insert a title and description", Toast.LENGTH_SHORT).show()
            return
        }
        val data = Intent()
        data.putExtra(EXTRA_FIRST_NAME, title)
        data.putExtra(EXTRA_LAST_NAME, description)
        setResult(Activity.RESULT_OK, data)
        finish()
    }

    companion object {
        const val EXTRA_FIRST_NAME = "EXTRA_TITLE"
        const val EXTRA_LAST_NAME = "EXTRA_DESCRIPTION"
    }
}