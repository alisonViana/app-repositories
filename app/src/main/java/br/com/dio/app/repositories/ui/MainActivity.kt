package br.com.dio.app.repositories.ui

import android.content.Intent
import android.os.Bundle
import android.os.Message
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.app.ShareCompat
import androidx.core.view.isEmpty
import br.com.dio.app.repositories.R
import br.com.dio.app.repositories.core.createDialog
import br.com.dio.app.repositories.core.createProgressDialog
import br.com.dio.app.repositories.core.hideSoftKeyboard
import br.com.dio.app.repositories.databinding.ActivityMainBinding
import br.com.dio.app.repositories.presentation.MainViewModel
import br.com.dio.app.repositories.ui.DetailActivity.Companion.REPO_EXTRA
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private val dialog by lazy { createProgressDialog() }
    private val viewModel by viewModel<MainViewModel>()
    private val adapter by lazy { RepoListAdapter() }
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        setMessage(R.string.label_initial)
        setListeners()

        binding.rvRepoList.adapter = adapter

        viewModel.repos.observe(this){
            when (it) {
                MainViewModel.State.Loading -> dialog.show()
                is MainViewModel.State.Error -> {
                    dialog.dismiss()
                    createDialog {
                        setMessage(it.error.message)
                    }.show()
                }
                is MainViewModel.State.Success -> {
                    dialog.dismiss()
                    binding.tvMessages.visibility = View.INVISIBLE
                    if (it.list.isEmpty()) setMessage(R.string.label_repo_empty)
                    adapter.submitList(it.list)
                }
            }
        }
    }

    private fun setMessage(message: Int) {
        binding.tvMessages.setText(message)
        binding.tvMessages.visibility = View.VISIBLE
    }

    private fun setListeners() {
        adapter.clickListener = {
            Intent(this, DetailActivity::class.java).apply {
                putExtra(REPO_EXTRA, it)
                startActivity(this)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        val searchView = menu.findItem(R.id.action_search).actionView as SearchView
        searchView.setOnQueryTextListener(this)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.let { viewModel.getRepoList(it) }
        binding.root.hideSoftKeyboard()
        Log.i(TAG, "$query")
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        Log.i(TAG, "$newText")
        return false
    }

    companion object {
        const val TAG = "TAG"
    }
}