package com.reactright.reactright.util

import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class FirebaseMessage {
    companion object {
        fun getLocalizedMessage(errorCode : String) : String {
            return when(errorCode) {
                "ERROR_WEAK_PASSWORD" -> "Enter a 6-digit password"
                "ERROR_INVALID_EMAIL" -> "Enter a valid Email"
                else-> "Check your information again."
            }
        }
    }
}

fun Fragment.showMessage(title :String, detail : String) {
    MaterialAlertDialogBuilder(requireContext())
        .setTitle(title)
        .setMessage(detail)
//        .setNegativeButton("Reject") { dialog, which->
//
//        }
        .setPositiveButton("Okey")  { dialog, which->

        }
        .show()
}