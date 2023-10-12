package com.example.chall3.ui.login

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.example.chall3.R
import com.example.chall3.databinding.ActivityLoginBinding
import com.example.chall3.ui.register.RegisterActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val email = binding.etEmail
        val password = binding.etPassword.toString()
        val login = binding.btLogin
        val loading = binding.progressBar

        loginViewModel = ViewModelProvider(this, LoginViewModelFactory())[LoginViewModel::class.java]

        loginViewModel.loginResult.observe(this@LoginActivity, Observer {
            val loginResult = it ?: return@Observer

            if (loading != null) {
                loading.visibility = View.GONE
            }
            if (loginResult.error != null) {
                showLoginFailed(loginResult.error)
            }
            if (loginResult.success != null) {
                updateUiWithUser(loginResult.success)
            }
            setResult(Activity.RESULT_OK)

            finish()
        })

        isDataValid()
        login()
        moveToRegister()
    }

    private fun login() {
        binding.btLogin!!.setOnClickListener {
            Log.d("BtLogin", "Button clicked, ${isDataValid()}")

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
        Log.d("BtLogin", "Button clicked, ${emailEt.emailValid()}, ${passwordEt.isPasswordValid()}")

    }

    private fun updateUiWithUser(model: LoggedInUserView) {
        val welcome = getString(R.string.welcome)
        val displayName = model.displayName
        // TODO : initiate successful logged in experience
        Toast.makeText(
            applicationContext,
            "$welcome $displayName",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar!!.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}