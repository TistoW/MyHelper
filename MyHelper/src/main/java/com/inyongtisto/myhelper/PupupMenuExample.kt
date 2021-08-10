package com.inyongtisto.myhelper

import android.content.Context
import android.view.View
import android.widget.EditText
import androidx.appcompat.widget.PopupMenu
import com.inyongtisto.myhelper.extension.logs

fun Context.example() {
    val view: EditText = EditText(this)
    val popupMenu = PopupMenu(this, view)
    popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener {
        logs(it.toString())
        when (it.toString()) {
            "Hapus Riwayat" -> logs("do this")
            "Baca" -> logs("do this")
        }
        return@OnMenuItemClickListener true
    })
    popupMenu.menu.add("Hapus Riwayat")
    popupMenu.menu.add("Baca")
    popupMenu.show()
}