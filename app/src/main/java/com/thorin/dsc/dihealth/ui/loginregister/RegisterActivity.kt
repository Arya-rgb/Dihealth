package com.thorin.dsc.dihealth.ui.loginregister

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.jakewharton.rxbinding2.widget.RxTextView
import com.thorin.dsc.dihealth.R
import com.thorin.dsc.dihealth.databinding.ActivityRegisterBinding
import com.thorin.dsc.dihealth.ui.mainpage.HomeActivity
import io.reactivex.Observable


class RegisterActivity : AppCompatActivity() {

    private var _binding: ActivityRegisterBinding? = null
    private val binding get() = _binding!!

    private val ref = FirebaseAuth.getInstance()

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnDaftar.isEnabled = false
        binding.btnDaftar.setBackgroundColor(ContextCompat.getColor(this, android.R.color.darker_gray))

        title = "Register"

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

        val passwordConfirmationStream = Observable.merge(
            RxTextView.textChanges(binding.idPassword)
                .map { password ->
                    password.toString() != binding.idPasswordConfirm.text.toString()
                },
            RxTextView.textChanges(binding.idPasswordConfirm)
                .map { confirmPassword ->
                    confirmPassword.toString() != binding.idPassword.text.toString()
                }
        )
        passwordConfirmationStream.subscribe {
            showPasswordConfirmationAlert(it)
        }


        val invalidFieldsStream = Observable.combineLatest(
            emailStream,
            passwordStream,
            passwordConfirmationStream
        ) { emailInvalid: Boolean, passwordInvalid: Boolean, passwordConfirmationInvalid: Boolean ->
            !emailInvalid && !passwordInvalid && !passwordConfirmationInvalid
        }
        invalidFieldsStream.subscribe { isValid ->
            if (isValid) {
                binding.btnDaftar.isEnabled = true
                binding.btnDaftar.setBackgroundColor(ContextCompat.getColor(this, R.color.purple_500))
            } else {
                binding.btnDaftar.isEnabled = false
                binding.btnDaftar.setBackgroundColor(ContextCompat.getColor(this, android.R.color.darker_gray))
            }
        }

        binding.btnDaftar.setOnClickListener {
            daftar()
        }

    }

    private fun daftar() {
        val progressdialog = ProgressDialog(this)
        progressdialog.setMessage("Loading...")

        progressdialog.show()

        ref.createUserWithEmailAndPassword(
            binding.idEmail.text.toString().trim(),
            binding.idPassword.text.toString().trim()
        )
            .addOnSuccessListener {
                progressdialog.dismiss()
                val email = ref.currentUser?.email
                Toast.makeText(this, "Register Successfully With Email  $email", Toast.LENGTH_SHORT)
                    .show()
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            }
            .addOnFailureListener { e ->
                progressdialog.dismiss()
                Toast.makeText(
                    this,
                    "Register Failed Due To ${e.message}",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
    }

    private fun showEmailExistAlert(isNotValid: Boolean) {
        binding.idEmail.error = if (isNotValid) getString(R.string.email_not_valid) else null
    }

    private fun showPasswordMinimalAlert(isNotValid: Boolean) {
        binding.idPassword.error = if (isNotValid) getString(R.string.password_not_valid) else null
    }

    private fun showPasswordConfirmationAlert(isNotValid: Boolean) {
        binding.idPasswordConfirm.error = if (isNotValid) getString(R.string.password_not_same) else null
    }

}


