package com.example.aboutnews

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        cartaTodo.setOnClickListener({callAllNews("")})
        cartaCiencia.setOnClickListener({callAllNews("science")})
        cartaNegocios.setOnClickListener({callAllNews("business")})
        cartaEntretenimiento.setOnClickListener({callAllNews("entertainment")})
        cartaDeportes.setOnClickListener({callAllNews("sports")})
        cartaTecnologia.setOnClickListener({callAllNews("technology")})
        cartaMedicina.setOnClickListener({callAllNews("health")})
        cartaPolitica.setOnClickListener({callAllNews("politics")})
    }
    fun callAllNews(categoria: String){
        val intent = Intent(this,CategoryAnsw::class.java).apply {
            putExtra("data",categoria)
        }
        startActivity(intent)
    }


}