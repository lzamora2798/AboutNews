package com.example.aboutnews

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_category_answ.*
import kotlinx.android.synthetic.main.row_new.view.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.text.SimpleDateFormat

class CategoryAnsw : AppCompatActivity() {
    private lateinit var resultados: JSONArray
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_answ)
        val categoriaClick = intent.getStringExtra("data").toString()
        callAllNews(categoriaClick)
    }

    fun callAllNews(categoria:String){
        val queue = Volley.newRequestQueue(this)
        val url = "https://bing-news-search1.p.rapidapi.com/news?category=" + categoria+"&mkt=en-us"
        val jsonObjectRequest = object: JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener<JSONObject> { response ->
                //textView.text = "Response: %s".format(response.toString())
                println(response.toString())
                showResult(response)
                progressBar.setVisibility(View.INVISIBLE)
            },
            Response.ErrorListener { error ->
                println("Esto es un error")
            }
        ){
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["x-bingapis-sdk"] = "true"
                headers["x-rapidapi-key"] = "4e546adb93msh3d487c146de5bfap1446efjsn4a7b168fdbe0"
                headers["x-rapidapi-host"] = "bing-news-search1.p.rapidapi.com"
                return headers
            }
        }
        queue.add(jsonObjectRequest)

        //MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }
    fun showResult(resultado:JSONObject) {
        val resultados: JSONArray
        resultados = resultado.getJSONArray("value")
        listview.adapter = CustomAdapter(this, resultados)
        listview.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            createIntent(resultados.get(position) as JSONObject)
        }
    }

    private class CustomAdapter(categoryAnsw: Context,resultados:JSONArray) : BaseAdapter() {

        private val mContext :Context = categoryAnsw
        private val results: JSONArray = resultados

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val layputInflater = LayoutInflater.from(mContext)
            val rowNoticia = layputInflater.inflate(R.layout.row_new,parent,false)
            val objetoNoticia = results.get(position) as JSONObject
            rowNoticia.descripcion.text = objetoNoticia.getString("name")
            rowNoticia.fecha.text = convertirDate(objetoNoticia.getString("datePublished"))
            try{
                val urlImage = objetoNoticia.getJSONObject("image").getJSONObject("thumbnail").getString("contentUrl")
                Picasso.get()
                    .load(urlImage)
                    .resize(50, 50)
                    .centerCrop()
                    .into(rowNoticia.imageView)
            }catch (e: JSONException){
                Picasso.get()
                    .load("https://images.unsplash.com/photo-1572949645841-094f3a9c4c94?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=334&q=80")
                    .resize(50, 50)
                    .centerCrop()
                    .into(rowNoticia.imageView)
            }

            return rowNoticia
        }

        override fun getItem(position: Int): Any {
            TODO("Not yet implemented")
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return results.length()
        }

        fun convertirDate(fecha:String): String{
            val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            val formatter = SimpleDateFormat("HH:mm dd-MM-yyyy")
            val output = formatter.format(parser.parse(fecha)).toString()
            return output
        }


    }
    fun createIntent(objeto: JSONObject){
        val intent = Intent(this,NoticiaCompleta::class.java).apply {
            putExtra("titulo",objeto.getString("name"))
            putExtra("url",objeto.getString("url"))
            putExtra("descripcion",objeto.getString("description"))
        }
        startActivity(intent)
    }
}