package com.reactright.reactright

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.reactright.reactright.databinding.ActivityLoginBinding
import com.reactright.reactright.ui.fragments.RegisterFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        if (auth.currentUser == null) {
            // Eğer kullanıcı oturum açmamışsa, giriş fragmentini aç
            supportFragmentManager.beginTransaction()
                .replace(R.id.navLogin, RegisterFragment())
                .commit()
        } else {
            // Eğer kullanıcı oturum açmışsa, ana ekrana geç
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}