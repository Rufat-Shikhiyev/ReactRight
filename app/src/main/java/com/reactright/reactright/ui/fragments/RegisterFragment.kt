package com.reactright.reactright.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.reactright.reactright.MainActivity
import com.reactright.reactright.R
import com.reactright.reactright.databinding.FragmentRegisterBinding
import com.reactright.reactright.util.FirebaseMessage
import com.reactright.reactright.util.showMessage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding

    private val firestore = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)


        helperText()


        binding.button.setOnClickListener {
            register()
        }

        return binding.root
    }

    fun register() {
        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()
        val username = binding.usernameEditText.text.toString()
        val number = binding.numberEditText.text.toString()
        val fincode = binding.finCodeEditText.text.toString()

        if (email.isBlank() || password.isBlank() || username.isBlank() || number.isBlank() || fincode.isBlank()) {

            showMessage(getString(R.string.oops), getString(R.string.something_went_wrong))
            return
        }

        val firebaseAuth = Firebase.auth
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { authResult ->
                val user = authResult.user
                addExtraUserInfo(user!!.uid)

                openMain()
            }.addOnFailureListener { exception ->
                (exception as? FirebaseAuthException)?.errorCode?.let { errorCode ->
                    showMessage("Oops", FirebaseMessage.getLocalizedMessage(errorCode))
                }
            }
    }


    private fun addExtraUserInfo(userId: String) {
        val username = binding.usernameEditText.text.toString()
        val number = binding.numberEditText.text.toString()
        val fincode = binding.finCodeEditText.text.toString()


        val email = binding.emailEditText.text.toString()

        val userDocument = firestore.collection("USERS").document(userId)
        userDocument.set(
            mapOf(
                "username" to username,
                "number" to number,
                "fin-code" to fincode,
                "email" to email
            )
        )
    }

    private fun helperText(){
        binding.emailEditText.setOnFocusChangeListener{_,focused->
            if(!focused){
                binding.emailEditTextLayout.helperText = valid_email()

            }
        }

        binding.passwordEditText.setOnFocusChangeListener{_, focused->
            if(!focused){
                binding.passwordEditTextLayout.helperText = valid_password()
            }
        }

        binding.finCodeEditText.setOnFocusChangeListener{_, focused->
            if(!focused){
                binding.finCodeEditTextLayout.helperText = valid_fincode()
            }
        }
    }

    private fun valid_password() : String? {

        val passwordtext = binding.passwordEditText.text.toString()

        return when {
            passwordtext.length < 6 -> "* * Minimum character 8"
            else -> null
        }

    }

    private fun valid_fincode(): String? {
        val fincode = binding.finCodeEditText.text.toString()

        return when {
            fincode.length < 6 -> "* Fin-Code must be 6 digits."
            else -> null
        }
    }


    private fun valid_email() : String? {

        val emailText = binding.emailEditText.text.toString()


        if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
            return "* Invalid Email"
        }

        return null
    }




    fun openMain() {
        val intent = Intent(requireContext(), MainActivity::class.java)
        startActivity(intent)
    }


}