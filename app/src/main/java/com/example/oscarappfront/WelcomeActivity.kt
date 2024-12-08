package com.example.oscarappfront

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        val tvToken = findViewById<TextView>(R.id.tvToken)
        tvToken.text = "Token: ${VoteSession.userToken}"

        findViewById<Button>(R.id.btnVoteMovie).setOnClickListener {
            startActivity(Intent(this, VoteMovieActivity::class.java))
        }
        findViewById<Button>(R.id.btnVoteDirector).setOnClickListener {
            startActivity(Intent(this, VoteDirectorActivity::class.java))
        }
        findViewById<Button>(R.id.btnConfirmVote).setOnClickListener {
            startActivity(Intent(this, ConfirmVoteActivity::class.java))
        }
        findViewById<Button>(R.id.btnLogout).setOnClickListener {
            VoteSession.clearSession()
            finish()
        }
    }
}
