package com.example.oscarappfront

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley

class VoteDirectorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vote_director)

        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroupDirectors)
        val btnVote = findViewById<Button>(R.id.btnVoteDirector)

        loadDirectors(progressBar, radioGroup)

        btnVote.setOnClickListener {
            val selectedRadioButtonId = radioGroup.checkedRadioButtonId
            if (selectedRadioButtonId == -1) {
                Toast.makeText(this, "Por favor, selecione um diretor.", Toast.LENGTH_SHORT).show()
            } else {
                val selectedRadioButton = findViewById<RadioButton>(selectedRadioButtonId)
                val directorId = selectedRadioButton.tag as Long
                VoteSession.selectedDirectorId = directorId
                VoteSession.selectedDirectorName = selectedRadioButton.text.toString()
                Toast.makeText(this, "Diretor selecionado com sucesso!", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun loadDirectors(progressBar: ProgressBar, radioGroup: RadioGroup) {
        val url = "http://wecodecorp.com.br/ufpr/diretor"

        progressBar.visibility = View.VISIBLE
        val request = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                progressBar.visibility = View.GONE
                for (i in 0 until response.length()) {
                    val director = response.getJSONObject(i)
                    val radioButton = RadioButton(this).apply {
                        text = director.getString("nome")
                        tag = director.getLong("id")
                    }
                    radioGroup.addView(radioButton)

                    println("Adicionado diretor: ${radioButton.text}, ID: ${radioButton.tag}")
                }
            },
            { error ->
                progressBar.visibility = View.GONE
                Toast.makeText(this, "Erro ao carregar diretores", Toast.LENGTH_SHORT).show()
                println("Erro ao carregar diretores: ${error.message}")
            }
        )

        Volley.newRequestQueue(this).add(request)
    }

}
