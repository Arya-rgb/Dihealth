package com.thorin.dsc.dihealth.ui.loginregister

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.jakewharton.rxbinding2.widget.RxTextView
import com.thorin.dsc.dihealth.R
import com.thorin.dsc.dihealth.databinding.ActivityLoginEmailBinding
import com.thorin.dsc.dihealth.ui.mainpage.HomeActivity
import io.reactivex.Observable

class LoginEmailActivity : AppCompatActivity() {

    private var _binding: ActivityLoginEmailBinding? = null
    private val binding get() = _binding!!

    private lateinit var mAuth: FirebaseAuth

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityLoginEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnDaftar.isEnabled = false
        binding.btnDaftar.setBackgroundColor(
            ContextCompat.getColor(
                this,
                android.R.color.darker_gray
            )
        )

        title = "Login"


        val emailStream = RxTextView.textChanges(binding.idEmail)
            .skipInitialValue()
            .map { email ->
                !Patterns.EMAIL_ADDRESS.matcher(email).matches()
            }
        emailStream.subscribe {
            showEmailExistAlert(it)
        }

        val passwordStream = RxTextView.textChanges(binding.idPassword)
            .skipInitialValue()
            .map { password ->
                password.length < 6
            }

        passwordStream.subscribe {
            showPasswordMinimalAlert(it)
        }


        val invalidFieldsStream = Observable.combineLatest(
            emailStream,
            passwordStream,
        ) { emailInvalid: Boolean, passwordInvalid: Boolean ->
            !emailInvalid && !passwordInvalid
        }
        invalidFieldsStream.subscribe { isValid ->
            if (isValid) {
                binding.btnDaftar.isEnabled = true
                binding.btnDaftar.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.purple_500
                    )
                )
            } else {
                binding.btnDaftar.isEnabled = false
                binding.btnDaftar.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        android.R.color.darker_gray
                    )
                )
            }
        }

        loginWithEmail()

    }

    private fun loginWithEmail() {
        val progressdialog = ProgressDialog(this)
        progressdialog.setMessage("Loading...")

        progressdialog.show()

        mAuth.signInWithEmailAndPassword(
            binding.idEmail.text.toString().trim(),
            binding.idPassword.text.toString().trim()
        )
            .addOnSuccessListener {
                progressdialog.dismiss()
                Intent(this, HomeActivity::class.java).also {
                    startActivity(it)
                    finish()
                }
            }
            .addOnFailureListener { e ->
                progressdialog.dismiss()
                Toast.makeText(
                    this,
                    "Login Failed Due To ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    private fun showEmailExistAlert(isNotValid: Boolean) {
        binding.idEmail.error = if (isNotValid) getString(R.string.email_not_valid) else null
    }

    private fun showPasswordMinimalAlert(isNotValid: Boolean) {
        binding.idPassword.error = if (isNotValid) getString(R.string.password_not_valid) else null
    }

}
