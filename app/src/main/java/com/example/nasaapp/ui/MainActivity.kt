package com.example.nasaapp.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.nasaapp.R
import com.example.nasaapp.ui.single_article_details.SingleArticleActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button = findViewById<Button>(R.id.btn)
        button.setOnClickListener {
            Toast.makeText(this, "fdsdsf", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, SingleArticleActivity::class.java)
            intent.putExtra("nasa_id", "PIA13517")
            this.startActivity(intent)
        }

    }
}
