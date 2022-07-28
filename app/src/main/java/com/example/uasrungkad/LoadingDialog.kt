package com.example.uasrungkad

import android.app.Activity
import android.app.AlertDialog

class LoadingDialog(private val HomeActivity:Activity) {
    private lateinit var isdialog:AlertDialog
    fun startLoading(){
        /**set View*/
        val infalter = HomeActivity.layoutInflater
        val dialogView = infalter.inflate(R.layout.loading_item,null)
        /**set Dialog*/
        val bulider = AlertDialog.Builder(HomeActivity)
        bulider.setView(dialogView)
        bulider.setCancelable(false)
        isdialog = bulider.create()
        isdialog.show()
    }
    fun isDismiss(){
        isdialog.dismiss()
    }
}