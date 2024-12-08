package com.example.oscarappfront

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MovieAdapter(private val movies: List<Movie>, private val onClick: (Movie) -> Unit) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    inner class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNome: TextView = view.findViewById(R.id.tvNome)
        val tvGenero: TextView = view.findViewById(R.id.tvGenero)
        val ivPoster: ImageView = view.findViewById(R.id.ivPoster)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.tvNome.text = movie.nome
        holder.tvGenero.text = movie.genero
        Glide.with(holder.itemView.context).load(movie.fotoUrl).into(holder.ivPoster)

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, MovieDetailActivity::class.java).apply {
                putExtra("MOVIE_NAME", movie.nome)
                putExtra("MOVIE_GENRE", movie.genero)
                putExtra("MOVIE_POSTER", movie.fotoUrl)
                putExtra("MOVIE_ID", movie.id)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = movies.size
}

