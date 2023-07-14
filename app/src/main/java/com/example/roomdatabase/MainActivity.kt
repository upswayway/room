package com.example.roomdatabase

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomdatabase.databinding.ActivityMainBinding
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    private var madapter: UserAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = Color.BLACK
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)


        initView()

        binding?.addButton?.setOnClickListener {
            Intent(this@MainActivity, AddNewActivity::class.java).apply {
                startActivity(this)
            }
        }
    }

    private fun initView() {
        binding?.apply {
            setSupportActionBar(toolbar)
            title = "RoomDatabase"
        }
    }

    private fun setAdapter(list: List<User>) {
        madapter?.setData(list)
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            val userList = AppDatabase(this@MainActivity).getUserDao().getAllUser()

            madapter = UserAdapter()
            binding?.recyclerView?.apply {
                layoutManager = LinearLayoutManager(this@MainActivity)
                adapter = madapter
                setAdapter(userList)


                madapter?.setOnActionEditListener {
                    val intent = Intent(this@MainActivity, AddNewActivity::class.java)
                    intent.putExtra("Data", it)
                    startActivity(intent)
                }
                madapter?.setOnActionDeleteListener {
                    val builder = AlertDialog.Builder(this@MainActivity)
                    builder.setMessage("Are you sure you want to delete?")
                    builder.setPositiveButton(
                        "Yes"
                    ) { YES, _ ->
                        lifecycleScope.launch {
                            AppDatabase(this@MainActivity).getUserDao().deleteUser(it)
                            val list = AppDatabase(this@MainActivity).getUserDao().getAllUser()
                            setAdapter(list)
                        }
                        YES.dismiss()
                    }
                    builder.setNegativeButton("No") { NO, _ ->
                        NO.dismiss()
                    }

                    val dialog = builder.create()
                    dialog.show()
                }
            }
        }

    }
}