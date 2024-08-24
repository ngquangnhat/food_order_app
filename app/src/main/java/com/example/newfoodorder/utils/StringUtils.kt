package com.example.newfoodorder.utils

object StringUtil {

    @JvmStatic
    fun isValidEmail(target: CharSequence?): Boolean {
        return target?.let {
            android.util.Patterns.EMAIL_ADDRESS.matcher(it).matches()
        } ?: false
    }

    @JvmStatic
    fun isGoodField(input: String?): Boolean {
        return !input.isNullOrEmpty() && input.length >= 6
    }

    @JvmStatic
    fun isEmpty(input: String?): Boolean {
        return input.isNullOrEmpty() || input.trim().isEmpty()
    }
}
