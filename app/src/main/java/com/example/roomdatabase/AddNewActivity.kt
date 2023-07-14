package com.example.roomdatabase

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.roomdatabase.databinding.ActivityAddNewBinding
import kotlinx.coroutines.launch


@Suppress("DEPRECATION")
class AddNewActivity : AppCompatActivity() {
    private var binding: ActivityAddNewBinding? = null
    private var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = Color.BLACK
        binding = ActivityAddNewBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        initView()

        user = intent.getSerializableExtra("Data") as User?

        binding?.addOrUpdateUser?.setOnClickListener { addUser() }

        if (user == null) {
            binding?.addOrUpdateUser?.text = "Add User"
        } else {
            binding?.addOrUpdateUser?.text = "Update"
            binding?.Name?.setText(user?.Name.toString())
            binding?.Designation?.setText(user?.Designation.toString())
            binding?.Company?.setText(user?.Company.toString())
        }

    }

    private fun initView() {
        binding?.apply {
            setSupportActionBar(toolbar)
            title = "RoomDatabase"
        }
    }

    private fun addUser() {
        val name = binding?.Name?.text.toString()
        val designation = binding?.Designation?.text.toString()
        val company = binding?.Company?.text.toString()

        lifecycleScope.launch {
            if (user == null) {
                val user = User(0, Name = name, Designation = designation, Company = company)
                AppDatabase(this@AddNewActivity).getUserDao().addUser(user)
                finish()
            } else {
                val u = User(0, name, designation, company)
                u.id = user?.id ?: 0
                AppDatabase(this@AddNewActivity).getUserDao().updateUser(u)
                finish()
            }
        }
    }
}