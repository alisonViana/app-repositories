package br.com.dio.app.repositories.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import br.com.dio.app.repositories.R
import br.com.dio.app.repositories.core.createDialog
import br.com.dio.app.repositories.core.createProgressDialog
import br.com.dio.app.repositories.core.hideSoftKeyboard
import br.com.dio.app.repositories.databinding.ActivityMainBinding
import br.com.dio.app.repositories.presentation.MainViewModel
import br.com.dio.app.repositories.ui.DetailActivity.Companion.REPO_EXTRA
import com.google.android.material.bottomsheet.BottomSheetBehavior
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private val dialog by lazy { createProgressDialog() }
    private val viewModel by viewModel<MainViewModel>()
    private val adapter by lazy { RepoListAdapter() }
    private val favoriteAdapter by lazy { FavoriteListAdapter() }
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        setBottomSheet()
        setMessage(R.string.label_initial)
        setListeners()

        binding.rvRepoList.adapter = adapter
        binding.bottomSheetLayout.rvFavorites.adapter = favoriteAdapter

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

    private fun setBottomSheet() {
        val sheetBehavior = BottomSheetBehavior.from(findViewById(R.id.bottom_sheet_layout))
        sheetBehavior.isHideable = false
        sheetBehavior.peekHeight = 80
        setBottomSheetState(COLLAPSED)

        viewModel.getFavoriteList().observe(this){
            favoriteAdapter.submitList(it)
            binding.bottomSheetLayout.tvFavoriteTitle.text =
                String.format(getString(R.string.favorites_number), it.size)
        }
    }

    private fun setBottomSheetState(state: String) {
        val sheetBehavior = BottomSheetBehavior.from(findViewById(R.id.bottom_sheet_layout))
        when (state) {
            EXPANDED -> sheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            COLLAPSED -> sheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }

    private fun setMessage(message: Int) {
        binding.tvMessages.setText(message)
        binding.tvMessages.visibility = View.VISIBLE
    }

    private fun setListeners() {
        // Listener dos detalhes dos repositórios
        adapter.clickListener = {
            Intent(this, DetailActivity::class.java).apply {
                putExtra(REPO_EXTRA, it)
                startActivity(this)
            }
        }

        // Listener para adicionar o user aos favoritos
        adapter.longClickListener = {
            //Toast.makeText(this, it.owner.login, Toast.LENGTH_SHORT).show()
            AlertDialog.Builder(this).apply {
                setTitle(R.string.delete_dialog_title)
                setMessage(String.format(getString(R.string.delete_dialog_message), it.owner.login))
                setPositiveButton(R.string.delete_dialog_positive){_,_ ->
                    viewModel.addFavorite(it.owner)
                }
                setNegativeButton(R.string.delete_dialog_negative, null)
            }.show()
        }

        // Listener do botão de excluir favorito
        favoriteAdapter.deleteFavoriteListener = {
            viewModel.removeFavorite(it)
        }

        // Listener para abrir os repositórios do user selecionado
        favoriteAdapter.clickFavoriteListener = {
            viewModel.getRepoList(it.userName)
            setBottomSheetState(COLLAPSED)
        }

        // Listener do botão de expansão do bottomSheet de favoritos

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
        const val EXPANDED = "EXPANDED"
        const val COLLAPSED = "COLLAPSED"
    }
}