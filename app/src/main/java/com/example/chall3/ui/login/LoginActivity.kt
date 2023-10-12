package com.example.chall3.ui.login

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.example.chall3.R
import com.example.chall3.databinding.ActivityLoginBinding
import com.example.chall3.ui.MainActivity
import com.example.chall3.ui.register.RegisterActivity
import com.example.chall3.utils.Result

class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loginViewModel = ViewModelProvider(this, LoginViewModelFactory())[LoginViewModel::class.java]

        isDataValid()
        loginUser()
        moveToRegister()
    }

    private fun loginUser() {
        binding.btLogin!!.setOnClickListener {
            val email = binding.etEmail!!.text.toString()
            val password = binding.etPassword!!.text.toString()
            login(email, password)
        }
    }

    private fun login(email: String, password: String) {
        loginViewModel.login(email, password)
        showLoading(true)

        loginViewModel.loginResult.observe(this@LoginActivity) {
            showLoading(false)
            when (it) {
                is Result.Success -> {
                    Toast.makeText(
                        this@LoginActivity, getString(R.string.login_succed), Toast.LENGTH_SHORT)
                        .show()

                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }

                is Result.Error -> {
                    Toast.makeText(
                        this@LoginActivity, it.exception.toString(), Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun moveToRegister() {
        binding.tvRegHere!!.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun isDataValid() {
        val emailEt = binding.etEmail!!
        val passwordEt = binding.etPassword!!
        val btLogin = binding.btLogin!!

        emailEt.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
            @Suppress("DEPRECATION")
            override fun afterTextChanged(s: Editable?) {
                if (emailEt.emailValid() && passwordEt.isPasswordValid()) {
                    btLogin.isEnabled = true
                    btLogin.setBackgroundColor(resources.getColor(R.color.light_magenta))
                } else {
                    btLogin.isEnabled = false
                    btLogin.setBackgroundColor(resources.getColor(R.color.light_grey))
                }
            }
        })

        passwordEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            @Suppress("DEPRECATION")
            override fun afterTextChanged(p0: Editable?) {
                if (emailEt.emailValid() && passwordEt.isPasswordValid()) {
                    btLogin.isEnabled = true
                    btLogin.setBackgroundColor(resources.getColor(R.color.light_magenta))
                } else {
                    btLogin.isEnabled = false
                    btLogin.setBackgroundColor(resources.getColor(R.color.light_grey))
                }
            }
        })

    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar!!.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}