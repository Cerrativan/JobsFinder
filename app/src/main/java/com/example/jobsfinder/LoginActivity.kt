package com.example.jobsfinder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.jobsfinder.R
import com.example.jobsfinder.databinding.ActivityLoginBinding
import com.example.jobsfinder.databinding.ActivityMainBinding
import com.example.jobsfinder.ui.service.UserRepository


class LoginActivity : AppCompatActivity() {

    private lateinit var loginBinding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        /*loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBinding.root)

        val loginNavController = this.findNavController(R.id.nav_host_fragment_login_activity)*/

        supportActionBar?.hide()
    }
}