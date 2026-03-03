package com.example.test.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.example.test.R
import com.example.test.data.UserDao
import java.io.File

class UserFormActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_USER_ID = "extra_user_id"
    }

    private lateinit var editName: EditText
    private lateinit var editMobile: EditText
    private lateinit var editPassword: EditText
    private lateinit var imagePhoto: ImageView
    private lateinit var btnTakePhoto: Button
    private lateinit var btnSave: Button

    private lateinit var dao: UserDao

    private var userId: Int? = null
    private var currentPhotoPath: String? = null
    private var pendingPhotoUri: Uri? = null

    private val requestCameraPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            startCameraCapture()
        } else {
            Toast.makeText(this, "Permiso de cámara denegado", Toast.LENGTH_SHORT).show()
        }
    }

    private val takePictureLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success) {
            if (currentPhotoPath != null) {
                imagePhoto.setImageURI(Uri.fromFile(File(currentPhotoPath!!)))
            }
        } else {
            pendingPhotoUri = null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_form)

        dao = UserDao(this)

        editName = findViewById(R.id.editTextUserName)
        editMobile = findViewById(R.id.editTextUserMobile)
        editPassword = findViewById(R.id.editTextUserPassword)
        imagePhoto = findViewById(R.id.imageViewUserPhoto)
        btnTakePhoto = findViewById(R.id.buttonTakePhoto)
        btnSave = findViewById(R.id.buttonSaveUser)

        userId = if (intent.hasExtra(EXTRA_USER_ID)) intent.getIntExtra(EXTRA_USER_ID, -1).takeIf { it > 0 } else null

        if (userId != null) {
            loadUser(userId!!)
        }

        btnTakePhoto.setOnClickListener {
            val hasCameraPermission = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED

            if (hasCameraPermission) {
                startCameraCapture()
            } else {
                requestCameraPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }

        btnSave.setOnClickListener {
            saveUser()
        }
    }

    private fun loadUser(id: Int) {
        val user = dao.getById(id) ?: return
        editName.setText(user.name)
        editMobile.setText(user.mobile)
        editPassword.setText(user.password)
        currentPhotoPath = user.photoPath

        if (!currentPhotoPath.isNullOrBlank()) {
            imagePhoto.setImageURI(Uri.fromFile(File(currentPhotoPath!!)))
        } else {
            imagePhoto.setImageDrawable(null)
        }
    }

    private fun startCameraCapture() {
        try {
            val photoFile = createImageFile()
            val uri = FileProvider.getUriForFile(
                this,
                applicationContext.packageName + ".fileprovider",
                photoFile
            )

            pendingPhotoUri = uri
            currentPhotoPath = photoFile.absolutePath
            takePictureLauncher.launch(uri)
        } catch (e: Exception) {
            Toast.makeText(this, "No se pudo abrir la cámara: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun createImageFile(): File {
        val imagesDir = File(filesDir, "user_photos")
        if (!imagesDir.exists()) imagesDir.mkdirs()
        return File.createTempFile("user_", ".jpg", imagesDir)
    }

    private fun saveUser() {
        val name = editName.text.toString().trim()
        val mobile = editMobile.text.toString().trim()
        val password = editPassword.text.toString().trim()

        if (name.isEmpty() || mobile.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        val id = userId
        if (id == null) {
            dao.insert(name, mobile, password, currentPhotoPath)
            Toast.makeText(this, "Usuario creado", Toast.LENGTH_SHORT).show()
        } else {
            dao.update(id, name, mobile, password, currentPhotoPath)
            Toast.makeText(this, "Usuario actualizado", Toast.LENGTH_SHORT).show()
        }

        finish()
    }
}
