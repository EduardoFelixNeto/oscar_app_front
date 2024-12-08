package com.example.oscarappfront

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley

class VoteMovieActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vote_movie)

        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        loadMovies(progressBar, recyclerView)
    }

    private fun loadMovies(progressBar: ProgressBar, recyclerView: RecyclerView) {
        val url = "http://wecodecorp.com.br/ufpr/filme"

        progressBar.visibility = View.VISIBLE
        val request = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                progressBar.visibility = View.GONE
                val movies = mutableListOf<Movie>()
                for (i in 0 until response.length()) {
                    val jsonMovie = response.getJSONObject(i)
                    movies.add(Movie(
                        jsonMovie.getLong("id"),
                        jsonMovie.getString("nome"),
                        jsonMovie.getString("genero"),
                        jsonMovie.getString("foto")
                    ))
                }
                recyclerView.adapter = MovieAdapter(movies) { movie ->
                    VoteSession.selectedMovieId = movie.id
                    startActivity(Intent(this, MovieDetailActivity::class.java).apply {
                        putExtra("MOVIE_ID", movie.id)
                    })
                }
            },
            { error ->
                progressBar.visibility = View.GONE
                Toast.makeText(this, "Erro ao carregar filmes", Toast.LENGTH_SHORT).show()
            }
        )

        Volley.newRequestQueue(this).add(request)
    }

}
