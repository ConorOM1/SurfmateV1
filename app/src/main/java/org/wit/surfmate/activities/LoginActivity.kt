package org.wit.surfmate.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import org.wit.surfmate.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    // Declare a binding variable
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize the binding variable
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnReset.setOnClickListener {
            // Clearing user_name and password edit text views on reset button click
            binding.etUserName.text.clear()
            binding.etPassword.text.clear()
        }

        // Set on-click listener for Submit button
        binding.btnSubmit.setOnClickListener {
            val userName = binding.etUserName.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            // Hardcoded credentials for testing
            val testUsername = "user"
            val testPassword = "password"

            if (userName == testUsername && password == testPassword) {
                Toast.makeText(this, "Login Successful!", Toast.LENGTH_LONG).show()

                val intent = Intent(this, SurfmateListActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Invalid Credentials!", Toast.LENGTH_LONG).show()
            }
        }
    }
}
