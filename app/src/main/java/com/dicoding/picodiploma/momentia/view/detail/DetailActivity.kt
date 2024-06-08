package com.dicoding.picodiploma.momentia.view.detail

import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dicoding.picodiploma.momentia.R
import com.dicoding.picodiploma.momentia.data.remote.model.ListStoryItem
import com.dicoding.picodiploma.momentia.databinding.ActivityDetailBinding
import com.dicoding.picodiploma.momentia.utils.DateHelper

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var story: ListStoryItem

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()

        story = intent.getParcelableExtra("STORY")!!

        binding.tvDetailName.text = story.name
        val dateHelper = DateHelper(this)
        binding.tvDetailTime.text = dateHelper.formatToDisplay(story.createdAt)
        binding.tvDetailDescription.text = story.description

        Glide.with(this)
            .load(story.photoUrl)
            .centerCrop()
            .into(binding.ivDetailPhoto)
    }

    private fun setupView() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setTitle(R.string.story_detail)
            setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}