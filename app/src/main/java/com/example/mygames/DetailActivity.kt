package com.example.mygames

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mygames.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    companion object {
        const val KEY_GAME = "key_game"
    }

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val datagame = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(KEY_GAME, Game::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(KEY_GAME)
        }

        datagame?.let {
            with(binding) {
                detailTitle.text = it.name
                detailDescription.text = it.description
                profileImage.setImageResource(it.photo)
                detailInfo.text = it.info
            }
        }

        binding.actionShare.setOnClickListener {
            shareButtonClicked(
                "Hello! I’ve just downloaded an app that contains some beautiful games recommendations. Check it out!\nLink: https://www.dicoding.com/home"
            )
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }

            R.id.about_page -> {
                val aboutIntent = Intent(this, AboutActivity::class.java)
                startActivity(aboutIntent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun shareButtonClicked(description: String) {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, description)

        startActivity(Intent.createChooser(shareIntent, "Share via"))
    }
}