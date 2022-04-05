package com.thorin.dsc.dihealth.ui.navigation.forum

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialog
import com.google.firebase.auth.FirebaseAuth
import com.thorin.dsc.dihealth.R
import com.thorin.dsc.dihealth.data.source.remote.response.UserPostResponse
import com.thorin.dsc.dihealth.databinding.ActivityEditProfileBinding
import com.thorin.dsc.dihealth.databinding.DialogAddPostBinding

class AddPostItemDialog(context: Context, var dialogListener: AddPostListener) :
    AppCompatDialog(context) {


    private var _binding: DialogAddPostBinding? = null
    private val binding get() = _binding!!

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = DialogAddPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth = FirebaseAuth.getInstance()

        binding.buttonPush.setOnClickListener {

            val dataUserSharedPref: SharedPreferences? =
                context.getSharedPreferences("data_user_local", Context.MODE_PRIVATE)

            val name = dataUserSharedPref?.getString("name", null)
            val email = dataUserSharedPref?.getString("email", null)
            val photoUrl = dataUserSharedPref?.getString("photo_url", null)
            val uid = mAuth.currentUser?.uid
            val post = binding.edtEdittext.text.toString()

            if (post.isEmpty()) {
                Toast.makeText(context, "Please Enter Your Post", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val item = UserPostResponse(photoUrl, uid, name, email, post)
            dialogListener.onAddButtonClicked(item)
            dismiss()
        }

        binding.btnClear.setOnClickListener {
            binding.edtEdittext.setText("")
        }

        binding.btnCancel.setOnClickListener {
            cancel()
        }

    }

}