package edu.nuce.apps.demo.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import edu.nuce.apps.demo.R
import edu.nuce.apps.demo.databinding.ActivityMainBinding
import edu.nuce.apps.demo.result.EventObserver
import edu.nuce.apps.demo.util.executeAfter

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var userAdapter: UserAdapter
    private val mainViewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        userAdapter = UserAdapter(lifecycleOwner = this, eventActions = mainViewModel)
        binding.executeAfter {
            lifecycleOwner = this@MainActivity
            viewModel = mainViewModel
        }

        binding.recyclerview.adapter = userAdapter

        mainViewModel.usersUiData.observe(this, Observer {
            it ?: return@Observer
            userAdapter.submitList(it)
        })

        mainViewModel.errorMessage.observe(this, EventObserver {
            Log.e(TAG, "Error... $it")
        })

        mainViewModel.navigateToUserActivity.observe(this, EventObserver { id ->
            Intent(this, UserActivity::class.java).also {
                it.putExtra(UserActivity::class.java.simpleName, id)
                startActivity(it)
            }
        })
    }
    
    companion object {
        private const val TAG = "MainActivity"
    }
}