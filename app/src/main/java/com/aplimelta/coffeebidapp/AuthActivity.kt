package com.aplimelta.coffeebidapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aplimelta.coffeebidapp.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity() {

    private val binding: ActivityAuthBinding by lazy {
        ActivityAuthBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}