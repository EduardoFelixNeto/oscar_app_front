package com.example.oscarappfront

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btnLogin = findViewById<Button>(R.id.btnLogin)
        btnLogin.setOnClickListener {
            val email = findViewById<EditText>(R.id.etEmail).text.toString()
            val password = findViewById<EditText>(R.id.etPassword).text.toString()

            Log.d("LoginDebug", "Login button clicked. Email: $email, Password: $password")

            authenticateUser(email, password)
        }
    }

    private fun authenticateUser(email: String, password: String) {
        val url = "http://10.0.2.2:8080/auth/login"
        Log.d("LoginDebug", "Attempting to authenticate user with URL: $url")

        val jsonBody = JSONObject().apply {
            put("email", email)
            put("senha", password)
        }
        Log.d("LoginDebug", "Request Body: $jsonBody")

        val request = JsonObjectRequest(
            Request.Method.POST, url, jsonBody,
            { response ->
                Log.d("LoginDebug", "Response received: $response")

                try {
                    val userId = response.getLong("id")
                    val token = response.getInt("token")
                    Log.d("LoginDebug", "Authentication successful. UserID: $userId, Token: $token")

                    VoteSession.userToken = token
                    VoteSession.userId = userId
                    startActivity(Intent(this, WelcomeActivity::class.java))
                } catch (e: Exception) {
                    Log.e("LoginDebug", "Error parsing response: ${e.message}")
                    Toast.makeText(this, "Erro ao processar resposta", Toast.LENGTH_SHORT).show()
                }
            },
            { error ->
                Log.e("LoginDebug", "Error during request: ${error.networkResponse?.statusCode}, ${error.message}")
                Toast.makeText(this, "Erro ao autenticar", Toast.LENGTH_SHORT).show()
            }
        )

        Log.d("LoginDebug", "Request added to queue")
        Volley.newRequestQueue(this).add(request)
    }
}
