package com.example.oscarappfront

import VoteSession
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class MovieDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        val tvMovieName = findViewById<TextView>(R.id.tvMovieName)
        val tvMovieGenre = findViewById<TextView>(R.id.tvMovieGenre)
        val ivMoviePoster = findViewById<ImageView>(R.id.ivMoviePoster)
        val btnVoteMovie = findViewById<Button>(R.id.btnVoteMovie)

        val movieName = intent.getStringExtra("MOVIE_NAME") ?: "Nome não disponível"
        val movieGenre = intent.getStringExtra("MOVIE_GENRE") ?: "Gênero não disponível"
        val moviePoster = intent.getStringExtra("MOVIE_POSTER")
        val movieId = intent.getLongExtra("MOVIE_ID", -1)

        tvMovieName.text = movieName
        tvMovieGenre.text = movieGenre
        moviePoster?.let {
            Glide.with(this).load(it).into(ivMoviePoster)
        }

        btnVoteMovie.setOnClickListener {
            VoteSession.selectedMovieId = movieId
            VoteSession.selectedMovieName = movieName
            Toast.makeText(this, "Filme selecionado com sucesso!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
