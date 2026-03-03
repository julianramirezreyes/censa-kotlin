package com.example.test.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.test.db.ConnectionSQLiteHelper

class UserDao(context: Context) {
    private val dbHelper = ConnectionSQLiteHelper(context)

    fun insert(name: String, mobile: String, password: String, photoPath: String?): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("name", name)
            put("mobile", mobile)
            put("password", password)
            put("photo_path", photoPath)
        }
        return db.insert("user", null, values)
    }

    fun update(id: Int, name: String, mobile: String, password: String, photoPath: String?): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("name", name)
            put("mobile", mobile)
            put("password", password)
            put("photo_path", photoPath)
        }
        return db.update("user", values, "id = ?", arrayOf(id.toString()))
    }

    fun delete(id: Int): Int {
        val db = dbHelper.writableDatabase
        return db.delete("user", "id = ?", arrayOf(id.toString()))
    }

    fun getById(id: Int): UserRow? {
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            "user",
            arrayOf("id", "name", "mobile", "password", "photo_path"),
            "id = ?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )

        cursor.use {
            if (!it.moveToFirst()) return null
            return it.toUserRow()
        }
    }

    fun getAll(): List<UserRow> {
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            "user",
            arrayOf("id", "name", "mobile", "password", "photo_path"),
            null,
            null,
            null,
            null,
            "id DESC"
        )

        val result = mutableListOf<UserRow>()
        cursor.use {
            if (it.moveToFirst()) {
                do {
                    result.add(it.toUserRow())
                } while (it.moveToNext())
            }
        }
        return result
    }

    private fun Cursor.toUserRow(): UserRow {
        val id = getInt(getColumnIndexOrThrow("id"))
        val name = getString(getColumnIndexOrThrow("name"))
        val mobile = getString(getColumnIndexOrThrow("mobile"))
        val password = getString(getColumnIndexOrThrow("password"))
        val photoPath = if (isNull(getColumnIndexOrThrow("photo_path"))) null else getString(getColumnIndexOrThrow("photo_path"))
        return UserRow(id = id, name = name, mobile = mobile, password = password, photoPath = photoPath)
    }
}

data class UserRow(
    val id: Int,
    val name: String,
    val mobile: String,
    val password: String,
    val photoPath: String?
)
