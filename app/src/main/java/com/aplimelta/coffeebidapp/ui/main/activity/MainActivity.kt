package com.aplimelta.coffeebidapp.ui.main.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.aplimelta.coffeebidapp.R
import com.aplimelta.coffeebidapp.data.source.Result
import com.aplimelta.coffeebidapp.data.source.remote.response.ProfileResponse
import com.aplimelta.coffeebidapp.databinding.ActivityMainBinding
import com.aplimelta.coffeebidapp.ui.MainViewModel
import com.aplimelta.coffeebidapp.ui.ViewModelFactory
import com.aplimelta.coffeebidapp.ui.auth.activity.AuthActivity
import com.aplimelta.coffeebidapp.utils.toast
import com.google.android.material.appbar.MaterialToolbar

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel: MainViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    private val onBackPressedCallback: OnBackPressedCallback =
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                when (navController.currentDestination?.id) {
                    R.id.home_navigation -> {
                        finish()
                    }

                    else -> {
                        navController.popBackStack()
                    }
                }
            }
        }

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel.profile.observe(this) { result: Result<ProfileResponse?> ->
            when (result) {
                Result.Loading -> {
                    hideView(true)
                    this@MainActivity.toast("Loading...")
                }

                is Result.Error -> {
                    hideView(false)
                    showMessageEmpty(false)
                    showMessageError(true)
                }

                is Result.Success -> {
                    val profile = result.data
                    if (profile != null) {
                        hideView(false)
                        showMessageEmpty(false)
                        showMessageError(false)
                    } else {
                        hideView(false)
                        showMessageError(false)
                        showMessageEmpty(true)
                        startActivity(Intent(this@MainActivity, AuthActivity::class.java))
                        finish()
                    }
                }
            }
        }

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.main_fragment_container_view) as NavHostFragment
        navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(navController.graph)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.detail_navigation -> binding.mainBottomNavigation.visibility = View.GONE
                else -> binding.mainBottomNavigation.visibility = View.VISIBLE
            }
        }

        binding.mainBottomNavigation.setupWithNavController(navController)
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    private fun showMessageEmpty(state: Boolean) {
        binding.apply {
            layoutMessage.root.visibility = if (state) {
                layoutMessage.sivIllustrationImage.setImageResource(R.drawable.ic_undraw_empty)
                layoutMessage.tvTitleMessage.text = resources.getString(R.string.empty_message)
                layoutMessage.tvSubtitleMessage.text =
                    resources.getString(R.string.empty_message_subtitle)
                View.VISIBLE
            } else {
                View.INVISIBLE
            }
        }
    }

    private fun showMessageError(state: Boolean) {
        binding.apply {
            layoutMessage.root.visibility = if (state) {
                layoutMessage.sivIllustrationImage.setImageResource(R.drawable.ic_undraw_server_cluster)
                layoutMessage.tvTitleMessage.text = resources.getString(R.string.error_message)
                layoutMessage.tvTitleMessage.text =
                    resources.getString(R.string.error_message_subtitle)
                View.VISIBLE
            } else {
                View.INVISIBLE
            }
        }
    }

    private fun hideView(state: Boolean) {
        binding.container.visibility = if (state) {
            View.VISIBLE
        } else {
            View.INVISIBLE
        }
    }

    fun setupToolbar(toolbar: MaterialToolbar?) {
        this.setSupportActionBar(toolbar)

        toolbar?.setupWithNavController(navController, appBarConfiguration)
        toolbar?.setNavigationOnClickListener {
            navController.navigateUp() || super.onSupportNavigateUp()
        }
    }
}