package com.example.oscarappfront

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class ConfirmVoteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm_vote)

        val tvSelectedMovie = findViewById<TextView>(R.id.tvSelectedMovie)
        val tvSelectedDirector = findViewById<TextView>(R.id.tvSelectedDirector)
        val etToken = findViewById<EditText>(R.id.etToken)
        val btnConfirmVote = findViewById<Button>(R.id.btnConfirmVote)

        tvSelectedMovie.text = "Filme: ${VoteSession.selectedMovieName ?: "Não selecionado"}"
        tvSelectedDirector.text = "Diretor: ${VoteSession.selectedDirectorName ?: "Não selecionado"}"

        btnConfirmVote.setOnClickListener {
            val token = etToken.text.toString().toIntOrNull()
            if (token == null) {
                Toast.makeText(this, "Insira um token válido.", Toast.LENGTH_SHORT).show()
            } else {
                sendVote(token)
            }
        }
    }

    private fun sendVote(token: Int) {
        val url = "http://10.0.2.2:8080/votos"
        val jsonBody = JSONObject().apply {
            put("userId", VoteSession.userId)
            put("token", token)
            put("filmeId", VoteSession.selectedMovieId)
            put("diretorId", VoteSession.selectedDirectorId)
        }

        val request = JsonObjectRequest(
            Request.Method.POST, url, jsonBody,
            { response ->
                Toast.makeText(this, "Voto registrado com sucesso!", Toast.LENGTH_SHORT).show()
                VoteSession.clearSession()
                finish()
            },
            { error ->
                Toast.makeText(this, "Erro ao registrar voto: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        )

        Volley.newRequestQueue(this).add(request)
    }
}
