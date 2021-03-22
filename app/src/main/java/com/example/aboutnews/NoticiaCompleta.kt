package com.example.aboutnews

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.content.ContextCompat.startActivity
import kotlinx.android.synthetic.main.activity_noticia_completa.*

class NoticiaCompleta : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_noticia_completa)
        val titulo = intent.getStringExtra("titulo").toString()
        val descripcion = intent.getStringExtra("descripcion").toString()
        val urll = intent.getStringExtra("url").toString()
        webView.settings.javaScriptEnabled = true
        webView.loadUrl(urll)

        WebView.setWebContentsDebuggingEnabled(false)
    }
}


