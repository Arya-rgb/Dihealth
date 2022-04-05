package com.thorin.dsc.dihealth.ui.loginregister

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.thorin.dsc.dihealth.R
import com.thorin.dsc.dihealth.databinding.ActivityLoginBinding
import com.thorin.dsc.dihealth.ui.editprofile.EditProfileViewModel
import com.thorin.dsc.dihealth.ui.mainpage.HomeActivity
import com.thorin.dsc.dihealth.viewmodel.viewmodelfactory.ViewModelFactory

@Suppress("DEPRECATION")
class LoginActivity : AppCompatActivity() {

    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var mAuth: FirebaseAuth
    private lateinit var googleSignClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignClient = GoogleSignIn.getClient(this, gso)

        mAuth = FirebaseAuth.getInstance()

        supportActionBar?.hide()

        binding.textView.setOnClickListener {
            Intent(this, RegisterActivity::class.java).also {
                startActivity(it)
            }
        }

        binding.button2.setOnClickListener {
            Intent(this, LoginEmailActivity::class.java).also {
                startActivity(it)
            }
        }

        binding.button.setOnClickListener {
            signIn()
        }

    }

    private fun signIn() {
        val signInIntent = googleSignClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val exception = task.exception
            if (task.isSuccessful) {
                try {
                    val account = task.getResult(ApiException::class.java)!!
                    Log.d("Sign in Activity", "FirebaseAuthWithGoogle" + account.id)
                    fireBaseAuthWithGoogle(account.idToken!!)
                } catch (e: ApiException) {
                    Log.w("Sign in Activity", "Google Sign in failed")
                }
            } else {
                Log.w("Sign in Activity", exception.toString())
            }
        }
    }


    private fun fireBaseAuthWithGoogle(idToken: String) {
        binding.animationView.visibility = View.VISIBLE
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    val factory = ViewModelFactory.getInstance(this)
                    val viewModel =
                        ViewModelProvider(this, factory)[EditProfileViewModel::class.java]

                    viewModel.uploadDataUser(
                        mAuth.currentUser?.photoUrl.toString(),
                        mAuth.currentUser?.uid.toString(),
                        mAuth.currentUser?.displayName.toString(),
                        mAuth.currentUser?.email.toString()
                    ).observe(this) { data ->

                        val prefPreTest2: SharedPreferences =
                            this.getSharedPreferences("data_user_local", Context.MODE_PRIVATE)
                        val edit = prefPreTest2.edit()
                        edit?.putString("photo_url", mAuth.currentUser?.photoUrl.toString())
                        edit?.putString("name", mAuth.currentUser?.displayName.toString())
                        edit?.putString("email", mAuth.currentUser?.email.toString())
                        edit?.apply()

                    }

                    binding.animationView.visibility = View.GONE
                    val intent = Intent(this, HomeActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()

                } else {
                    binding.animationView.visibility = View.GONE
                    Log.w("sign in activity", "SignInWithCredential:failure")
                }
            }
    }

    companion object {
        private const val RC_SIGN_IN = 120
    }

}