package com.example.mygames

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class DetailActivity : AppCompatActivity() {
    companion object {
        const val KEY_GAME = "key_game"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val profileImage: ImageView = findViewById(R.id.profile_image)
        val detailTitle: TextView = findViewById(R.id.detail_title)
        val detailDescription: TextView = findViewById(R.id.detail_description)
        val detailInfo = findViewById<TextView>(R.id.detail_info)
        val shareButton = findViewById<Button>(R.id.action_share)

        val datagame = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(KEY_GAME, Game::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(KEY_GAME)
        }

        datagame?.let {
            detailTitle.text = it.name
            detailDescription.text = it.description
            profileImage.setImageResource(it.photo)
            detailInfo.text = it.info
        }

        shareButton.setOnClickListener {
            shareButtonClicked(
                "Hello! I\'ve just downloaded an app that contains some beautiful games recommendations. Check it out!\nLink: https://www.dicoding.com/home"
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