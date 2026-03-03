package com.example.test.ui

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.test.R
import com.example.test.data.UserDao
import com.example.test.data.UserRow
import com.google.android.material.floatingactionbutton.FloatingActionButton

class UsersListActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var fabAdd: FloatingActionButton
    private lateinit var dao: UserDao

    private var users: List<UserRow> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users_list)

        dao = UserDao(this)
        listView = findViewById(R.id.listViewUsers)
        fabAdd = findViewById(R.id.fabAddUser)

        fabAdd.setOnClickListener {
            startActivity(Intent(this, UserFormActivity::class.java))
        }

        listView.setOnItemClickListener { _, _, position, _ ->
            val user = users[position]
            val intent = Intent(this, UserFormActivity::class.java)
            intent.putExtra(UserFormActivity.EXTRA_USER_ID, user.id)
            startActivity(intent)
        }

        listView.setOnItemLongClickListener { _, _, position, _ ->
            val user = users[position]
            AlertDialog.Builder(this)
                .setTitle("Eliminar")
                .setMessage("¿Deseas eliminar a ${user.name}?")
                .setPositiveButton("Sí") { _, _ ->
                    dao.delete(user.id)
                    loadUsers()
                    Toast.makeText(this, "Usuario eliminado", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("No", null)
                .show()
            true
        }
    }

    override fun onResume() {
        super.onResume()
        loadUsers()
    }

    private fun loadUsers() {
        users = dao.getAll()
        val items = users.map { "#${it.id} - ${it.name} (${it.mobile})" }
        listView.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
    }
}
