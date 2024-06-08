package com.dicoding.picodiploma.momentia.view.main

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.momentia.R
import com.dicoding.picodiploma.momentia.databinding.ActivityMainBinding
import com.dicoding.picodiploma.momentia.utils.ViewModelFactory
import com.dicoding.picodiploma.momentia.view.addstory.AddStoryActivity
import com.dicoding.picodiploma.momentia.view.maps.MapsActivity
import com.dicoding.picodiploma.momentia.view.welcome.WelcomeActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvStories.layoutManager = LinearLayoutManager(this)

        viewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            }
        }

        setupView()
        setupAction()
        getData()
    }

    private fun getData() {
        val adapter = MainAdapter()
        binding.rvStories.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            }
        )
        viewModel.story.observe(this) {
            adapter.submitData(lifecycle, it)
        }
    }

    private fun setupView() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setTitle(R.string.app_name)
    }

    private fun setupAction() {
        binding.fabUpload.setOnClickListener {
            startActivity(Intent(this@MainActivity, AddStoryActivity::class.java))
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.activity_main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                val alertDialog = MaterialAlertDialogBuilder(this).apply {
                    setTitle(R.string.logout)
                    setMessage(R.string.logout_dialog_message)
                    setPositiveButton(R.string.dialog_positive_button) { _, _ ->
                        viewModel.logout()
                        showToast(getString(R.string.success_logout))
                    }
                    setNegativeButton(R.string.dialog_negative_button) { _, _ ->
                    }
                }.create()
                alertDialog.show()
                true
            }

            R.id.menu_map -> {
                startActivity(Intent(this@MainActivity, MapsActivity::class.java))
                true
            }

            R.id.menu_language -> {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}