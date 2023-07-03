package com.developeralamin.ecity.utlis

import androidx.appcompat.app.AlertDialog
import android.content.Context
import com.developeralamin.ecity.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

object Config {

    private var dialog: AlertDialog? = null

    fun showDialog(context: Context){
        dialog = MaterialAlertDialogBuilder(context)
            .setView(R.layout.dialog_wait)
            .setCancelable(false)
            .create()

        dialog!!.show()
    }
    fun hideDialog(){
        dialog!!.dismiss()
    }

}