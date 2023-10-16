package com.example.chall3.ui.register

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.chall3.R
import com.example.chall3.database.users.User
import com.example.chall3.database.users.UserDatabase
import com.example.chall3.databinding.ActivityRegisterBinding
import com.example.chall3.ui.login.LoginActivity
import com.example.chall3.utils.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var registerViewModel: RegisterViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        registerViewModel =
            ViewModelProvider(this, RegisterViewModelFactory())[RegisterViewModel::class.java]

        registerUser()
    }

    private fun registerUser() {
        binding.btRegister.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val userName = binding.etUsername.text.toString()
            val phoneNumber = binding.etPhone.text.toString()

            register(email, password, userName, phoneNumber)
        }
    }

    private fun register(email: String, password: String, userName: String, phoneNumber: String) {

        registerViewModel.register(email, password)
        showLoading(true)

        registerViewModel.registerResult.observe(this@RegisterActivity) {
            showLoading(false)
            when (it) {
                is Result.Success -> {
                    Toast.makeText(
                        this@RegisterActivity,
                        getString(R.string.register_succed), Toast.LENGTH_SHORT
                    ).show()

                    insertUserIntoDatabase(email, userName, phoneNumber)

                    val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }

                is Result.Error -> {
                    Toast.makeText(
                        this@RegisterActivity, it.exception.toString(), Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun insertUserIntoDatabase(email: String, userName: String, phoneNumber: String) {
        val user = User(email = email, userName = userName, phoneNumber = phoneNumber)
        val userDao = UserDatabase.getUserDataBase(this).userDao()

        CoroutineScope(Dispatchers.IO).launch {
            userDao.insert(user)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}