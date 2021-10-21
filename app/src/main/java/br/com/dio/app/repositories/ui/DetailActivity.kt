package br.com.dio.app.repositories.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.widget.Toast
import br.com.dio.app.repositories.data.model.Repo
import br.com.dio.app.repositories.databinding.ActivityDetailBinding
import com.bumptech.glide.Glide

class DetailActivity : AppCompatActivity() {

    private val binding by lazy { ActivityDetailBinding.inflate(layoutInflater) }
    private var repo: Repo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        getExtras()
        setViews()
        setListeners()
    }

    private fun getExtras() {
        repo = intent.getParcelableExtra(REPO_EXTRA)
    }

    private fun setViews() {
        binding.tvOwner.text = repo?.owner?.login
        binding.tvRepoName.text = repo?.name
        binding.tvRepoDescription.text = repo?.description
        binding.tvRepoLanguage.text = repo?.language
        binding.chipStar.text = repo?.stargazersCount.toString()
        binding.chipForks.text = repo?.forksCount.toString()
        binding.chipWatchers.text = repo?.watchersCount.toString()

        binding.tvRepoLink.text = repo?.htmlURL
        binding.tvRepoLink.movementMethod = LinkMovementMethod.getInstance()

        Glide.with(binding.root.context)
            .load(repo?.owner?.avatarURL)
            .into(binding.ivOwner)
    }

    private fun setListeners() {
        binding.btnClose.setOnClickListener {
            finish()
        }
        binding.chipWatchers.setOnClickListener {
            showToast("Watchers")
        }
        binding.chipStar.setOnClickListener {
            showToast("Stargazers")
        }
        binding.chipForks.setOnClickListener {
            showToast("Forks")
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object{
        const val REPO_EXTRA = "REPO_EXTRA"
    }
}