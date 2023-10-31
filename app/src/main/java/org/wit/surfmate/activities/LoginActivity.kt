package org.wit.surfmate.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import org.wit.surfmate.databinding.ActivityLoginBinding
import org.wit.surfmate.models.UserJSONStore
import org.wit.surfmate.models.UserModel
import org.wit.surfmate.models.UserStore

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var userStore: UserStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userStore = UserJSONStore(applicationContext)

        binding.btnSignup.setOnClickListener {
            val firstName = binding.etFirstName.text.toString().trim()
            val lastName = binding.etLastName.text.toString().trim()
            val email = binding.etUserEmail.text.toString().trim()
            val password = binding.etUserPassword.text.toString().trim()


            val newUser = UserModel(firstName = firstName, lastName = lastName, userEmail = email, userPassword = password)
            userStore.create(newUser)

            Toast.makeText(this, "Sign Up Successful!", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, SurfmateListActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.etUserEmail.text.toString().trim()
            val password = binding.etUserPassword.text.toString().trim()

            val user = userStore.login(email, password)
            if (user != null) {
                Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, SurfmateListActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Invalid Credentials!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

