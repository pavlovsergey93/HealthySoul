package com.gmail.pavlovsv93.healthysoul.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gmail.pavlovsv93.healthysoul.R

class HealthySoulActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.themeHealthySoul)
        setContentView(R.layout.activity_main)
    }
}