package com.aplimelta.coffeebidapp.ui.main.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.aplimelta.coffeebidapp.R
import com.aplimelta.coffeebidapp.databinding.ActivityMainBinding
import com.aplimelta.coffeebidapp.ui.MainViewModel
import com.aplimelta.coffeebidapp.ui.ViewModelFactory
import com.aplimelta.coffeebidapp.ui.auth.activity.AuthActivity

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel: MainViewModel by viewModels {
        ViewModelFactory.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel.profile.observe(this) { result ->
            if (result != null) {
                binding.container.visibility = View.VISIBLE
                Toast.makeText(this, "$result", Toast.LENGTH_LONG).show()
            } else {
                binding.container.visibility = View.INVISIBLE
                startActivity(Intent(this@MainActivity, AuthActivity::class.java))
                finish()
            }
        }

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.main_fragment_container_view) as NavHostFragment
        val navController = navHostFragment.navController
        binding.mainBottomNavigation.setupWithNavController(navController)

    }
}