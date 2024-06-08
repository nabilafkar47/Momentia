package com.dicoding.picodiploma.momentia.view.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.picodiploma.momentia.R
import com.dicoding.picodiploma.momentia.data.pref.UserModel
import com.dicoding.picodiploma.momentia.databinding.ActivityLoginBinding
import com.dicoding.picodiploma.momentia.utils.ViewModelFactory
import com.dicoding.picodiploma.momentia.view.main.MainActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class LoginActivity : AppCompatActivity() {
    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAction()
        playAnimation()
    }

    private fun setupView() {
        supportActionBar?.hide()
    }

    private fun setupAction() {
        binding.buttonLogin.setOnClickListener {
            val email = binding.edLoginEmail.text.toString()
            val password = binding.edLoginPassword.text.toString()
            viewModel.postLogin(email, password)
        }

        viewModel.alertMessage.observe(this) { event ->
            event.getContentIfNotHandled()?.let { alertMessage ->
                showAlert(alertMessage)
            }
        }

        viewModel.isLoading.observe(this) { isLoading ->
            showLoading(isLoading)
        }

        viewModel.isSessionSaved.observe(this) { isSessionSaved ->
            if (isSessionSaved) {
                moveActivity()
            }
        }
    }

    private fun showAlert(message: String) {
        val alertDialog = MaterialAlertDialogBuilder(this).apply {
            setTitle(R.string.login)
            setMessage(message)
            setPositiveButton(R.string.dialog_positive_button) { _, _ ->
                viewModel.loginResponse.value?.let { loginResponse ->
                    if (!loginResponse.error) {
                        viewModel.saveSession(
                            UserModel(
                                binding.edLoginEmail.text.toString(),
                                loginResponse.loginResult.token,
                                true)
                        )
                    }
                }
            }
        }.create()
        alertDialog.show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.pbLogin.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun moveActivity() {
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    private fun playAnimation() {
        val title = ObjectAnimator.ofFloat(binding.tvLoginTitle, View.ALPHA, 1f).setDuration(500)
        val message = ObjectAnimator.ofFloat(binding.tvLoginMessage, View.ALPHA, 1f).setDuration(500)
        val email = ObjectAnimator.ofFloat(binding.lLoginEmail, View.ALPHA, 1f).setDuration(500)
        val password = ObjectAnimator.ofFloat(binding.lLoginPassword, View.ALPHA, 1f).setDuration(500)
        val login = ObjectAnimator.ofFloat(binding.buttonLogin, View.ALPHA, 1f).setDuration(500)

        AnimatorSet().apply {
            playSequentially(title, message, email, password, login)
            startDelay = 500
            start()
        }
    }
}