package com.aplimelta.coffeebidapp.ui.auth.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.aplimelta.coffeebidapp.R
import com.aplimelta.coffeebidapp.databinding.ActivityAuthBinding
import com.aplimelta.coffeebidapp.ui.main.activity.MainActivity

class AuthActivity : AppCompatActivity() {

    private val binding: ActivityAuthBinding by lazy {
        ActivityAuthBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        hideSystemUI()

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.auth_fragment_container_view) as NavHostFragment
        val navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.login_navigation -> {
                    binding.authFooter.tvInfoAuth.text =
                        resources.getString(R.string.don_t_have_an_account_yet)
                    binding.authFooter.btnRegister.text = resources.getString(R.string.sign_up)
                    binding.authFooter.btnRegister.setOnClickListener {
                        navController.navigate(R.id.register_navigation)
                    }
                }

                else -> {
                    binding.authFooter.tvInfoAuth.text =
                        resources.getString(R.string.already_have_an_account)
                    binding.authFooter.btnRegister.text = resources.getString(R.string.sign_in)
                    binding.authFooter.btnRegister.setOnClickListener {
                        navController.navigate(R.id.login_navigation)
                    }
                }
            }
        }
    }

    fun directToMainActivity() {
        val intent = Intent(this@AuthActivity, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    private fun hideSystemUI() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.systemBars() or WindowInsets.Type.navigationBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
    }
}