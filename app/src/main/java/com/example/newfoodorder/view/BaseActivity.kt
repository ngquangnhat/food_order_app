package com.example.newfoodorder.view

import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.newfoodorder.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

abstract class BaseActivity : AppCompatActivity() {
    protected var progressDialog: AlertDialog? = null
    protected var alertDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createProgressDialog()
        createAlertDialog()
    }
    private fun createProgressDialog() {
        progressDialog = MaterialAlertDialogBuilder(this)
            .setMessage(R.string.waiting_message)
            .setCancelable(false)
            .create()
    }
    fun showProgressDialog(value: Boolean) {
        if (value) {
            if (progressDialog != null && !progressDialog!!.isShowing) {
                progressDialog!!.show()
            }
        } else {
            progressDialog?.dismiss()
        }
    }
    fun dismissProgressDialog() {
        progressDialog?.dismiss()
        alertDialog?.dismiss()
    }
    private fun createAlertDialog() {
        alertDialog = MaterialAlertDialogBuilder(this)
            .setTitle(R.string.app_name)
            .setPositiveButton(R.string.action_ok, null)
            .setCancelable(false)
            .create()
    }
    fun showAlertDialog(errorMessage: String) {
        alertDialog?.setMessage(errorMessage)
        alertDialog?.show()
    }

    fun showAlertDialog(@StringRes resourceId: Int) {
        alertDialog?.setMessage(getString(resourceId))
        alertDialog?.show()
    }

    fun setCancelProgress(isCancel: Boolean) {
        progressDialog?.setCancelable(isCancel)
    }
    override fun onDestroy() {
        progressDialog?.dismiss()
        alertDialog?.dismiss()
        super.onDestroy()
    }
}