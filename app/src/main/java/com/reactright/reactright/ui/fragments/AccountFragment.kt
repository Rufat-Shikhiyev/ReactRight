package com.reactright.reactright.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.reactright.reactright.R
import com.reactright.reactright.databinding.FragmentAccountBinding
import com.reactright.reactright.databinding.FragmentMainBinding
import com.reactright.reactright.util.showMessage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccountFragment : Fragment() {
    private lateinit var binding: FragmentAccountBinding

    private val firestore = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAccountBinding.inflate(inflater, container, false)

        val currentUser = FirebaseAuth.getInstance().currentUser

        currentUser?.uid?.let { userId ->
            getUserInfo(userId)
        }

        binding.buttonAccount.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            requireActivity().finish()
        }

        return binding.root
    }

    private fun getUserInfo(userId: String) {
        val userDocument = firestore.collection("USERS").document(userId)
        userDocument.get()
            .addOnSuccessListener { document ->
                val username = document.getString("username")
                val number = document.getString("number")
                val fincode = document.getString("fin-code")
                val email = document.getString("email")

                binding.usernameEditText.text = username
                binding.numberEditText.text = number
                binding.finCodeEditText.text = fincode
                binding.emailEditText.text = email
            }
            .addOnFailureListener {
                showMessage(getString(R.string.oops), getString(R.string.something_went_wrong))
            }
    }



}
